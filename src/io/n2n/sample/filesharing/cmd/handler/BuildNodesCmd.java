package io.n2n.sample.filesharing.cmd.handler;

import io.n2n.node.Node;
import io.n2n.sample.filesharing.FSNode;
import io.n2n.sample.filesharing.cmd.CommandHandler;

import java.util.List;
import java.util.Map;

/**
 * Command handler for building nodes <br>
 * Usage: <code>buildnodes <host> <port> [-h <hops>]</code>
 */
public class BuildNodesCmd extends CommandHandler {
    private Node node;

    @Override
    protected void addOptions() {
        super.addOptions();
        this.options.put("-h", 1);
    }

    public BuildNodesCmd(Node node) {
        this.node = node;
    }

    @Override
    public void onCommand(Node sender, String label, String[] args, Map<String, List<String>> options) {
        String host = args[0];
        int port = Integer.parseInt(args[1]);

        // hops
        int hops = 5; // default
        if (options.containsKey("-h")) {
            hops = Integer.parseInt(options.get("-h").get(0));
        }

        ((FSNode) this.node).buildNodes(host, port, hops);
    }
}
