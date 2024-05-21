package io.n2n.sample.filesharing.cmd.handler;

import io.n2n.node.Node;
import io.n2n.sample.filesharing.FSNode;
import io.n2n.sample.filesharing.cmd.CommandHandler;

import java.util.List;
import java.util.Map;

/**
 * Command handler for listing files <br>
 * Usage: <code>files</code>
 */
public class FileList extends CommandHandler {
    private Node node;

    public FileList(Node node) {
        this.node = node;
    }

    @Override
    public void onCommand(Node sender, String label, String[] args, Map<String, List<String>> options) {
        FSNode fsNode = (FSNode) node;
        System.out.println("[File mappings]");
        fsNode.getFiles().forEach((k, v) -> {
            System.out.println(k + " -> " + v);
        });
    }

    @Override
    public String helpMsg() {
        return ": List file mappings of the node";
    }
}
