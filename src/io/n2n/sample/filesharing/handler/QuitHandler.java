package io.n2n.sample.filesharing.handler;

import io.n2n.connection.Connection;
import io.n2n.connection.Handler;
import io.n2n.connection.Message;
import io.n2n.node.Node;
import io.n2n.node.NodeInfo;
import io.n2n.sample.filesharing.MessageType;

import java.util.List;

public class QuitHandler implements Handler {
    private Node node;

    public QuitHandler(Node node) {
        this.node = node;
    }
    @Override
    public void handle(Connection connection, Message msg) {
        String pid = msg.getData();
        NodeInfo node = this.node.getSettings().getNeighborManager().removeNode(pid);

        if (node != null) {
            String info = String.format("%s (%s:%d)", node.getId(), node.getHost(), node.getPort());
            System.out.println("[QuitHandler] " + info + " is removed from neighbor list");
            connection.sendData(new Message(MessageType.REPLY.name(), "Node removed: " + pid));
        } else{
            System.out.println("[QuitHandler] " + pid + " is not in neighbor list");
            connection.sendData(new Message(MessageType.ERROR.name(), "Node not found: " + pid));
        }
    }

    @Override
    public String getName() {
        return MessageType.QUIT.name();
    }
}
