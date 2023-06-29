package io.n2n.sample.messenger.handler;

import io.n2n.connection.Connection;
import io.n2n.connection.Handler;
import io.n2n.connection.Message;
import io.n2n.node.NeighborManager;
import io.n2n.node.Node;
import io.n2n.sample.messenger.MessengerMsgType;

/**
 * A handler used when need to quit from the network.
 * <p>
 * Data format: [id]
 */
public class QuitHandler implements Handler {
    private Node node;

    public QuitHandler(Node node) {
        this.node = node;
    }

    @Override
    public void handle(Connection connection, Message msg) {
        NeighborManager neighbors = this.node.getSettings().getNeighborManager();

        String id = msg.getData().strip();

        // contains or not
        if (!neighbors.hasNode(id)) {
            connection.sendData(new Message(MessengerMsgType.EROR.name(), "Doesn't contain the node"));
            return;
        }

        // remove the node
        neighbors.removeNode(id);
        connection.sendData(new Message(MessengerMsgType.RPLY.name(), "Node(" + id + ") has been removed"));
    }

    @Override
    public String getName() {
        return MessengerMsgType.QUIT.name();
    }
}
