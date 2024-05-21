package io.n2n.sample.filesharing.cmd.handler;

import io.n2n.connection.Message;
import io.n2n.node.Node;
import io.n2n.node.NodeInfo;
import io.n2n.sample.filesharing.MessageType;
import io.n2n.sample.filesharing.cmd.CommandHandler;

import java.util.List;
import java.util.Map;

/**
 * Join command handler <br>
 * Command: <code>join <<host> <port> | <id>></code>
 */
public class JoinCmd extends CommandHandler {
    private Node node;

    public JoinCmd(Node node) {
        this.node = node;
    }
    @Override
    public void onCommand(Node sender, String label, String[] args, Map<String, List<String>> options) {
        // if args length is 1
        NodeInfo nodeInfo = new NodeInfo("", "", 0);
        if (args.length == 1) {
            String pid = args[0];
            nodeInfo = this.node.getSettings().getNeighborManager().getNode(pid);
        }else if (args.length == 2) {
            String host = args[0];
            int port = Integer.parseInt(args[1]);
            nodeInfo = new NodeInfo("", host, port);
        }

        String myPid = this.node.getInfo().getId();
        String myHost = this.node.getInfo().getHost();
        int myPort = this.node.getInfo().getPort();

        // send join message
        Message msg = new Message(MessageType.JOIN.name(), myPid + " " + myHost + " " + myPort);
        Message reply = this.node.sendData(nodeInfo, msg).stream().findFirst().get();
        System.out.println("[JoinCmd] " + reply.getType() + ": " + reply.getData());
    }
}