package io.n2n.sample.filesharing.cmd.handler;

import io.n2n.connection.Message;
import io.n2n.node.Node;
import io.n2n.node.NodeInfo;
import io.n2n.sample.filesharing.MessageType;
import io.n2n.sample.filesharing.cmd.CommandHandler;

import java.util.List;
import java.util.Map;

/**
 * Command handler for the info command. <br>
 * Usage: <code>info <host> <port></code>
 */
public class InfoCmd extends CommandHandler {
    private Node node;

    public InfoCmd(Node node) {
        this.node = node;
    }
    @Override
    public void onCommand(Node sender, String label, String[] args, Map<String, List<String>> options) {
        // check if the number of arguments is correct
        String host = args[0];
        int port = Integer.parseInt(args[1]);

        // send a message to the specified host and port
        Message message = new Message(MessageType.NAME.name(), "");
        NodeInfo nodeInfo = new NodeInfo("", host, port);
        String id = node.sendData(nodeInfo, message).get(0).getData();

        // add the node to the neighbor list
        boolean isAdded = node.getSettings().getNeighborManager().addNode(new NodeInfo(id, host, port));
        if (isAdded) {
            System.out.println("Node added to the neighbor list: " + nodeInfo.toString());
        } else {
            System.out.println("Node already exists in the neighbor list: " + nodeInfo.toString());
        }
    }

    @Override
    public String helpMsg() {
        return "<host> <port>: Show information of the peer";
    }
}
