package io.n2n.sample.filesharing.handler;

import io.n2n.connection.Connection;
import io.n2n.connection.Handler;
import io.n2n.connection.Message;
import io.n2n.node.Node;
import io.n2n.node.NodeInfo;
import io.n2n.sample.filesharing.MessageType;

public class JoinHandler implements Handler {
    private Node node;

    public JoinHandler(Node node) {
        this.node = node;
    }

    @Override
    public void handle(Connection connection, Message msg) {
        String[] data = msg.getData().split(" ");
        String pid = data[0];
        String host = data[1];
        int port = Integer.parseInt(data[2]);

        // checking checking!
        if (node.getSettings().getNeighborManager().hasNode(pid)) {
            // check if the node is already in the neighbor list
            connection.sendData(new Message(MessageType.ERROR.name(), "Already in the neighbor list"));
            return;
        } else if(node.getSettings().getNeighborManager().isFull()) {
            // check neighbor list size
            connection.sendData(new Message(MessageType.ERROR.name(), "Neighbor list is full"));
            return;
        }

        // add the node to the neighbor list
        NodeInfo newNode = new NodeInfo(pid, host, port);
        node.getSettings().getNeighborManager().addNode(newNode);
        Message reply = new Message(MessageType.REPLY.name(), "Node added: " + newNode);
        connection.sendData(reply);
    }

    @Override
    public String getName() {
        return MessageType.JOIN.name();
    }
}
