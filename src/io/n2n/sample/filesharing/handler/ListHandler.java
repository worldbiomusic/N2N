package io.n2n.sample.filesharing.handler;

import io.n2n.connection.Connection;
import io.n2n.connection.Handler;
import io.n2n.connection.Message;
import io.n2n.node.Node;
import io.n2n.sample.filesharing.MessageType;

public class ListHandler implements Handler {
    private Node node;

    public ListHandler(Node node) {
        this.node = node;
    }

    @Override
    public void handle(Connection connection, Message msg) {
        // send count first
        Message count_reply = new Message(MessageType.REPLY.name(), "" + this.node.getSettings().getNeighborManager().getNodeCount());
        connection.sendData(count_reply);

        // send all nodes info (pid, host, port)
        this.node.getSettings().getNeighborManager().getNodes().forEach(node -> {
            String nodeInfo = node.getId() + " " + node.getHost() + " " + node.getPort();
            Message nodeInfo_reply = new Message(MessageType.REPLY.name(), nodeInfo);
            connection.sendData(nodeInfo_reply);
        });
    }

    @Override
    public String getName() {
        return MessageType.LIST.name();
    }
}
