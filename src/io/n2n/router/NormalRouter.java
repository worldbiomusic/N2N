package io.n2n.router;

import io.n2n.node.Node;
import io.n2n.node.NodeInfo;

/**
 * A normal router that attempts to look for a node
 * info in its neighbors only. If the target is not an
 * immediate neighbor, it's impossible to route and
 * return null.
 */
public class NormalRouter implements Router {
    private Node node;

    public NormalRouter(Node node) {
        this.node = node;
    }

    @Override
    public NodeInfo route(String id) {
        return this.node.getSettings().getNeighborManager().getNode(id);
    }
}
