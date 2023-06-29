package io.n2n.sample.messenger.handler;

import io.n2n.connection.Connection;
import io.n2n.connection.Handler;
import io.n2n.connection.Message;
import io.n2n.node.NeighborManager;
import io.n2n.node.Node;
import io.n2n.node.NodeInfo;
import io.n2n.sample.messenger.MessengerMsgType;
import io.n2n.sample.messenger.MessengerNode;

/**
 * A handler to insert a new incoming node info to my neighbors.
 * <p>
 * Data format: [id] [host] [port]
 */
public class JoinHandler implements Handler {
    private Node node;

    public JoinHandler(Node node) {
        this.node = node;
    }

    @Override
    public void handle(Connection connection, Message msg) {
        NeighborManager neighbors = this.node.getSettings().getNeighborManager();

        String[] data = msg.getData().split(MessengerNode.DELIMITER);
        String id, host;
        int port;

        // check exceptions
        try {
            id = data[0];
            host = data[1];
            port = Integer.parseInt(data[2]);
        } catch (Exception e) {
            connection.sendData(new Message(MessengerMsgType.EROR.name(), "Error while parsing arguments"));
            return;
        }

        // check is itself
        if(this.node.getInfo().getId().equals(id)) {
            connection.sendData(new Message(MessengerMsgType.EROR.name(), "Trying to add itself"));
            return;
        }

        // check for duplication
        if (neighbors.hasNode(id)) {
            connection.sendData(new Message(MessengerMsgType.EROR.name(), "Already exists"));
            return;
        }

        // check is full
        if (neighbors.isFull()) {
            System.out.println("Neighbors are full");
            connection.sendData(new Message(MessengerMsgType.EROR.name(), "Neighbors are full"));
            return;
        }

        NodeInfo newNode = new NodeInfo(id, host, port);
        neighbors.addNode(newNode);
        System.out.println("Added a new node: " + newNode);
        connection.sendData(new Message(MessengerMsgType.RPLY.name(), "Node has benn inserted"));
    }

    @Override
    public String getName() {
        return MessengerMsgType.JOIN.name();
    }
}
