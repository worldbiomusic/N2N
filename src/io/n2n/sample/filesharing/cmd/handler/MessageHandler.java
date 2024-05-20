package io.n2n.sample.filesharing.cmd.handler;

import io.n2n.connection.Message;
import io.n2n.node.Node;
import io.n2n.node.NodeInfo;
import io.n2n.sample.filesharing.cmd.CommandHandler;

import java.util.List;
import java.util.Map;

public class MessageHandler extends CommandHandler {
    @Override
    protected void addOptions() {
        this.options.put("-n", 1);
        this.options.put("-t", 1);
    }

    @Override
    public void onCommand(Node sender, String label, String[] args, Map<String, List<String>> options) {
        // msg <data> -n <id> -t <type> (-N <host> <port>)
        String id = options.get("-n").get(0);
        String type = options.get("-t").get(0);
        String data = String.join(" ", args);
        NodeInfo node = sender.getSettings().getNeighborManager().getNode(id);
        Message msg = new Message(type, data);
        sender.sendData(node, msg);
    }
}
