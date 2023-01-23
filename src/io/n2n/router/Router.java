package io.n2n.router;

import io.n2n.node.NodeInfo;

/**
 * Interface for objects that will be used to find a node
 * with the node's id and limited hops. In general, router can
 * work recursively with other nodes.
 */
public interface Router {
    NodeInfo route(String id, int hops);
}
