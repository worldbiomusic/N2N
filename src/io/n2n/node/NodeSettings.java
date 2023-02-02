package io.n2n.node;

import io.n2n.connection.Dispatcher;
import io.n2n.router.Router;
import io.n2n.stabilizer.Stabilizer;

/**
 * Manages node's settings like state, number of max nodes and tools(router and stabilizer) also.
 */
public class NodeSettings {
    private boolean active;
    private int maxNodes;
    private int backlog;
    private Router router;
    private Stabilizer stabilizer;
    private NeighborManager neighborManager;
    private Dispatcher dispatcher;

    public NodeSettings() {
        this.active = false;
        this.maxNodes = 5;
        this.backlog = 5;
//        this.router =  -> set with normal router
//        this.stabilizer = -> set with normal stabilizer
        this.neighborManager = new NeighborManager(this);
        this.dispatcher = new Dispatcher();
    }


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

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public NeighborManager getNeighborManager() {
        return neighborManager;
    }

    @Override
    public String toString() {
        return "NodeSettings{" +
                "active=" + active +
                ", maxNodes=" + maxNodes +
                ", backlog=" + backlog +
                ", router=" + router +
                ", stabilizer=" + stabilizer +
                ", neighborManager=" + neighborManager +
                ", dispatcher=" + dispatcher +
                '}';
    }
}
