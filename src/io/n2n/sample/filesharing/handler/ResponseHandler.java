package io.n2n.sample.filesharing.handler;

import io.n2n.connection.Connection;
import io.n2n.connection.Handler;
import io.n2n.connection.Message;
import io.n2n.node.Node;
import io.n2n.node.NodeInfo;
import io.n2n.sample.filesharing.FSNode;
import io.n2n.sample.filesharing.MessageType;

public class ResponseHandler implements Handler {
    private Node node;

    public ResponseHandler(Node node) {
        this.node = node;
    }
    @Override
    public void handle(Connection connection, Message msg) {
        // data format: <file name> <peer id> <host> <port>
        String[] data = msg.getData().split(" ");
        String fileName = data[0];
        String peerId = data[1];
        String host = data[2];
        int port = Integer.parseInt(data[3]);

        // insert the queried node info into the file list
        NodeInfo newNode = new NodeInfo(peerId, host, port);
        ((FSNode) node).getFiles().put(fileName, newNode);
    }

    @Override
    public String getName() {
        return MessageType.RESPONSE.name();
    }
}
