package io.n2n.sample.filesharing.cmd.handler;

import io.n2n.node.Node;
import io.n2n.sample.filesharing.cmd.CommandHandler;

import java.util.List;
import java.util.Map;

/**
 * Command handler for nodes <br>
 * Usage: <code>nodes</code>
 */
public class NodesCmd extends CommandHandler {
    private Node node;

    public NodesCmd(Node node) {
        this.node = node;
    }

    @Override
    public void onCommand(Node sender, String label, String[] args, Map<String, List<String>> options) {
        System.out.println("[Neighbor peers]");
        node.getSettings().getNeighborManager().getNodes().forEach(n -> {
            // id, host, port
            System.out.println(n.getId() + " -> " + n.getHost() + ":" + n.getPort());
        });
    }

    @Override
    public String helpMsg() {
        return ": List neighbor peers";
    }
}
