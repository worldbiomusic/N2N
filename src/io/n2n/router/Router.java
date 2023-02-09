package io.n2n.router;

import io.n2n.node.NodeInfo;

/**
 * Interface for objects that will be used to find a node
 * with the node's id and limited hops. In general, router
 * works recursively with other nodes.
 */
public interface Router {
    /**
     * Routes a node with id.
     *
     * @param id the Node's id
     * @return NodeInfo if found or null
     */
    NodeInfo route(String id);
}
