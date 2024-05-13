package io.n2n.sample.filesharing.handler;

import io.n2n.connection.Connection;
import io.n2n.connection.Handler;
import io.n2n.connection.Message;
import io.n2n.node.Node;
import io.n2n.node.NodeInfo;
import io.n2n.sample.filesharing.FSNode;
import io.n2n.sample.filesharing.MessageType;

import java.util.Map;

public class QueryHandler implements Handler {
    private Node node;

    public QueryHandler(Node node) {
        this.node = node;
    }

    @Override
    public void handle(Connection connection, Message msg) {
        String returnPID, key, ttl;
        try {
            String[] data = msg.getData().split(" ");
            returnPID = data[0];
            key = data[1];
            ttl = data[2];

            Message ack = new Message(MessageType.REPLY.name(), "ACK: " + msg.getData());
            connection.sendData(ack);
        } catch (Exception e) {
            System.out.println("Error in QueryHandler: " + e.getMessage());
            Message error = new Message(MessageType.ERROR.name(), "Query error: " + e.getMessage());
            connection.sendData(error);
            return;
        }

        // broadcast query
        Thread thread = new Thread(() -> broadcastQuery(returnPID, key, ttl));
        thread.start();
    }

    private void broadcastQuery(String returnPID, String key, String ttl) {
        Map<String , NodeInfo> files = ((FSNode) node).getFiles();

    }

    @Override
    public String getName() {
        return MessageType.QUERY.name();
    }
}
