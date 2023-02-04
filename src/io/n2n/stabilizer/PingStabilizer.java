package io.n2n.stabilizer;

import io.n2n.connection.Connection;
import io.n2n.node.Node;
import io.n2n.node.NodeInfo;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * A stabilization routine that tries to establish a connection
 * with every neighbor of the node. If the connection failed,
 * removed the node from the neighbor list.
 */
public class PingStabilizer implements Stabilizer {
    private Node node;

    public PingStabilizer(Node node) {
        this.node = node;
    }

    @Override
    public void stabilize() {
        List<NodeInfo> neighbors = this.node.getSettings().getNeighborManager().getNodes();
        Iterator<NodeInfo> iterator = neighbors.iterator();

        while (iterator.hasNext()) {
            NodeInfo neighbor = iterator.next();
            try (Connection connection = new Connection(neighbor)) {
            } catch (IOException e) {
                iterator.remove();
            }
        }
    }
}
