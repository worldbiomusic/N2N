package io.n2n.node;

import io.n2n.connection.Dispatcher;
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
    private int backlog;
    private Router router;
    private Stabilizer stabilizer;
    private Map<String, NodeInfo> neighbors; // using Map for efficiency
    private Dispatcher dispatcher;


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getMaxNodes() {
        return maxNodes;
    }

    public void setMaxNodes(int maxNodes) {
        this.maxNodes = maxNodes;
    }

    public int getBacklog() {
        return backlog;
    }

    public void setBacklog(int backlog) {
        this.backlog = backlog;
    }

    public Router getRouter() {
        return router;
    }

    public void setRouter(Router router) {
        this.router = router;
    }

    public Stabilizer getStabilizer() {
        return stabilizer;
    }

    public void setStabilizer(Stabilizer stabilizer) {
        this.stabilizer = stabilizer;
    }

//    public Map<String, NodeInfo> getNeighbors() {
//        return neighbors;
//    }
//
//    public void setNeighbors(Map<String, NodeInfo> neighbors) {
//        this.neighbors = neighbors;
//    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }
}
