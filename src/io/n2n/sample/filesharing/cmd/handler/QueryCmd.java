package io.n2n.sample.filesharing.cmd.handler;

import io.n2n.connection.Message;
import io.n2n.node.Node;
import io.n2n.node.NodeInfo;
import io.n2n.sample.filesharing.MessageType;
import io.n2n.sample.filesharing.cmd.CommandHandler;

import java.util.List;
import java.util.Map;

/**
 * Query command handler <br>
 * Usage: <code>query [-t <ttl>] <file name></code>
 */
public class QueryCmd extends CommandHandler {
    private Node node;

    public QueryCmd(Node node) {
        this.node = node;
    }

    @Override
    protected void addOptions() {
        super.addOptions();
        this.options.put("-t", 1); // ttl option
    }

    @Override
    public void onCommand(Node sender, String label, String[] args, Map<String, List<String>> options) {
        String nid = this.node.getInfo().getId(); // get node id
        String fileName = args[0]; // get file name
        int ttl = 5; // default ttl
        if (options.containsKey("-t")) {
            ttl = Integer.parseInt(options.get("-t").get(0)); // get ttl
        }

        // asynchronously broadcast query message to neighbors
        Message msg = new Message(MessageType.QUERY.name(), nid + " " + fileName + " " + ttl);
        for (NodeInfo neighbor : this.node.getSettings().getNeighborManager().getNodes()) {
            new Thread(() -> query(this.node, neighbor, msg)).start(); // thread start
        }
    }

    private void query(Node node, NodeInfo target, Message msg) {
        Message reply = this.node.sendData(target, msg).stream().findFirst().get();
        System.out.println("Query result: " + reply.getType() + "(" + reply.getData() + ")");
    }

    @Override
    public String helpMsg() {
        return "";
    }
}
