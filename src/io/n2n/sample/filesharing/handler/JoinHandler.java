package io.n2n.sample.filesharing.handler;

import io.n2n.connection.Connection;
import io.n2n.connection.Handler;
import io.n2n.connection.Message;
import io.n2n.node.Node;
import io.n2n.sample.filesharing.MessageType;

public class JoinHandler implements Handler {
    private Node node;

    public JoinHandler(Node node) {
        this.node = node;
    }

    @Override
    public void handle(Connection connection, Message msg) {

    }

    @Override
    public String getName() {
        return MessageType.JOIN.name();
    }
}
