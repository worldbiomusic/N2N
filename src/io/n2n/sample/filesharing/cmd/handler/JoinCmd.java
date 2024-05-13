package io.n2n.sample.filesharing.cmd.handler;

import io.n2n.connection.Message;
import io.n2n.node.NeighborManager;
import io.n2n.node.Node;
import io.n2n.node.NodeInfo;
import io.n2n.sample.filesharing.cmd.CommandExecutor;
import io.n2n.sample.messenger.MessengerMsgType;

import java.util.List;

/**
 * Command: join [target-host] [target-port]
 */
public class JoinCmd implements CommandExecutor {
    @Override
    public void onCommand(Node sender, String label, String[] args) {
        String targetHost = args[0];
        int targetPort = Integer.parseInt(args[1]);
        NodeInfo target = new NodeInfo("", targetHost, targetPort);
        List<Message> replies = sender.sendData(target, new Message(MessengerMsgType.NAME.name(), ""));
        if (replies.isEmpty() || !replies.get(0).getType().equals(MessengerMsgType.RPLY.name())) {
            System.out.println("Couldn't get node's id");
        }
        String targetId = replies.get(0).getData();

        NeighborManager neighborManager = sender.getSettings().getNeighborManager();
        if (!neighborManager.hasNode(targetId)) {
            target.setId(targetId);
            neighborManager.addNode(target);
            System.out.println("Added a new node: " + target);
        }

        String id = sender.getInfo().getId();
        String host = sender.getInfo().getHost();
        String port = sender.getInfo().getPort() + "";
        String msgData = String.format("%s %s %s", id, host, port);
        Message msg = new Message(MessengerMsgType.JOIN.name(), msgData);
        replies = sender.sendData(target, msg);

        if(replies.isEmpty() || MessengerMsgType.RPLY.name().equals(replies.get(0))) {
            System.out.println("Error: target couldn't insert node");
        }else {
            System.out.println("Node has been inserted to the target's neighbor list");
        }
    }
}