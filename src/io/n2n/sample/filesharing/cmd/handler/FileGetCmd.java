package io.n2n.sample.filesharing.cmd.handler;

import io.n2n.connection.Message;
import io.n2n.node.Node;
import io.n2n.node.NodeInfo;
import io.n2n.sample.filesharing.FSNode;
import io.n2n.sample.filesharing.MessageType;
import io.n2n.sample.filesharing.cmd.CommandHandler;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * File get command handler <br>
 * Usage: <code>fget <filename></code>
 */
public class FileGetCmd extends CommandHandler {
    private Node node;

    public FileGetCmd(Node node) {
        this.node = node;
    }

    @Override
    public void onCommand(Node sender, String label, String[] args, Map<String, List<String>> options) {
        String filename = args[0];

        // send file get request
        FSNode fsNode = (FSNode) this.node;
        fsNode.getFiles().entrySet().stream()
                .filter(entry -> entry.getKey().equals(filename))
                .findFirst()
                .ifPresent(entry -> {
                    NodeInfo targetNode = entry.getValue();

                    // send msg
                    Message msg = new Message(MessageType.FILE_GET.name(), filename);
                    Message reply = this.node.sendData(targetNode, msg).stream().findFirst().get();

                    // handle reply
                    if (reply.getType().equals(MessageType.REPLY.name())) {
                        // download file and save to local
                        String filedata = reply.getData();
                        try (FileWriter writer = new FileWriter(filename)) {
                            writer.write(filedata);
                            System.out.println("[FileGetCmd] File downloaded: " + filename);
                        } catch (IOException e) {
                            System.out.println("[FileGetCmd] Error writing file: " + filename);
                        }
                    } else {
                        System.out.println("[FileGetCmd] " + reply.getType() + ": " + reply.getData());
                    }
                });
    }
}
