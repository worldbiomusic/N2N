package io.n2n.sample.filesharing.stabilizer;

import io.n2n.node.Node;
import io.n2n.sample.filesharing.FSNode;
import io.n2n.stabilizer.Stabilizer;

public class PingStabilizer implements Stabilizer {
    private Node node;

    public PingStabilizer(Node node) {
        this.node = node;
    }

    @Override
    public void stabilize() {

    }
}
