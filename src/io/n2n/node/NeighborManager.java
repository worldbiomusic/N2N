package io.n2n.node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages neighbor nodes.
 */
public class NeighborManager {
    private NodeSettings settings;
    private Map<String, NodeInfo> nodes;

    public NeighborManager(NodeSettings settings) {
        this.settings = settings;
        this.nodes = new HashMap<>();
    }

    public List<NodeInfo> getNodes() {
        return (List<NodeInfo>) nodes.values();
    }

    public boolean addNode(NodeInfo node) {
        if (isFull() || containsNode(node.getId())) {
            return false;
        }

        this.nodes.put(node.getId(), node);
        return true;
    }

    public NodeInfo removeNode(String id) {
        return this.nodes.remove(id);
    }

    public NodeInfo getNode(String id) {
        return this.nodes.get(id);
    }

    public boolean containsNode(String id) {
        return this.nodes.containsKey(id);
    }

    public int getNodeCount() {
        return getNodes().size();
    }

    /**
     * Check number of neighbors is reached to {@link NodeSettings#getMaxNodes()}.
     *
     * @return true if full, or false
     */
    public boolean isFull() {
        return this.settings.getMaxNodes() <= getNodeCount();
    }

    @Override
    public String toString() {
        return "NeighborManager{" +
                "nodes=" + getNodes() +
                '}';
    }
}
