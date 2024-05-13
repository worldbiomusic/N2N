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
        Thread thread = new Thread(() -> broadcastQuery(returnPID, key, Integer.parseInt(ttl)));
        thread.start();
    }

    private void broadcastQuery(String returnPID, String key, int ttl) {
        Map<String, NodeInfo> files = ((FSNode) node).getFiles();

        // find a peer who has the file
        for (Map.Entry<String, NodeInfo> entry : files.entrySet()) {
            String fileName = entry.getKey();
            NodeInfo node = entry.getValue();
            if (fileName.contains(key)) {
                String data = fileName + " " + node.getId() + " " + node.getHost() + " " + node.getPort();
                Message msg = new Message(MessageType.RESPONSE.name(), data);
                this.node.sendData(node, msg);
            }
        }

        // broadcast to other peers if ttl > 0
        if (ttl > 0) {
            Message msg = new Message(MessageType.QUERY.name(), returnPID + " " + key + " " + (ttl - 1));
            this.node.getSettings().getNeighborManager().getNodes().forEach(n -> this.node.sendData(n.getId(), msg));
        }
    }

    @Override
    public String getName() {
        return MessageType.QUERY.name();
    }
}
