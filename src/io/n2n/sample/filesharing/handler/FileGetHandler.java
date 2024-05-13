package io.n2n.sample.filesharing.handler;

import io.n2n.connection.Connection;
import io.n2n.connection.Handler;
import io.n2n.connection.Message;
import io.n2n.node.Node;
import io.n2n.node.NodeInfo;
import io.n2n.sample.filesharing.FSNode;
import io.n2n.sample.filesharing.MessageType;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

public class FileGetHandler implements Handler {
    private Node node;

    public FileGetHandler(Node node) {
        this.node = node;
    }

    @Override
    public void handle(Connection connection, Message msg) {
        // data format: filename
        String filename = msg.getData();

        Map<String, NodeInfo> files = ((FSNode) node).getFiles();

        // check if file exists
        if (!files.containsKey(filename)) {
            System.out.println("[FileGetHandler] File not found: " + filename);
            connection.sendData(new Message(MessageType.ERROR.name(), "File not found: " + filename));
            return;
        }

        // send file data
        StringBuilder filedata = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(filename)) {
            byte[] buffer = new byte[2048];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                filedata.append(new String(buffer, 0, bytesRead));
            }
        } catch (IOException e) {
            System.out.println("[FileGetHandler] Error reading file: " + filename);
            connection.sendData(new Message(MessageType.ERROR.name(), "Error reading file: " + filename));
            return;
        }
        connection.sendData(new Message(MessageType.REPLY.name(), filedata.toString()));
    }

    @Override
    public String getName() {
        return MessageType.FILE_GET.name();
    }
}
