package io.n2n.node;

import io.n2n.connection.Handler;
import io.n2n.router.Router;
import io.n2n.stabilizer.Stabilizer;

import java.util.Map;

/**
 * Manages node's settings like state, number of max nodes and tools(router and stabilizer) also.
 */
public class NodeSettings {
    private boolean active;
    private int maxNodes;
    private Router router;
    private Stabilizer stabilizer;
    private Map<String, NodeInfo> neighbors; // using Map for efficiency
    private Map<String, Handler> handlers; // using Map for efficiency
}
