package io.n2n.sample.filesharing.cmd.handler;

import io.n2n.connection.Message;
import io.n2n.node.Node;
import io.n2n.sample.filesharing.MessageType;
import io.n2n.sample.filesharing.cmd.CommandHandler;

import java.util.List;
import java.util.Map;

/**
 * Coomand handler for the list command. <br>
 * Usage: <code>list <id></code>
 */
public class ListCmd extends CommandHandler {
    private Node node;

    public ListCmd(Node node) {
        this.node = node;
    }
    @Override
    public void onCommand(Node sender, String label, String[] args, Map<String, List<String>> options) {
        String id = args[0];

        // get the neighbor list
        Message msg = new Message(MessageType.LIST.name(), "");
        List<Message> msgs = this.node.sendData(id, msg);
        int count = Integer.parseInt(msgs.get(0).getData());
        System.out.println("Neighbor list of " + id + " has " + count + " nodes");

        System.out.println("\nID\tHost\tPort");
        for (int i = 1; i <= count; i++) {
            String data = msgs.get(i).getData();
            String pid = data.split(" ")[0];
            String host = data.split(" ")[1];
            String port = data.split(" ")[2];
            System.out.println(pid + "\t" + host + "\t" + port);
        }
    }

    @Override
    public String helpMsg() {
        return "<id>: List the neighbors of the peer";
    }
}
