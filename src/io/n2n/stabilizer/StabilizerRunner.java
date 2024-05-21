package io.n2n.stabilizer;

import io.n2n.node.Node;

/**
 * A class for stabilizing at specific intervals.
 */
public class StabilizerRunner extends Thread {
    private Node node;
    private Stabilizer stabilizer;
    private int delay;

    /**
     * Constructs an object.
     *
     * @param node
     * @param stabilizer the stabilizer to use
     * @param delay      the delay (in milliseconds)
     */
    public StabilizerRunner(Node node, Stabilizer stabilizer, int delay) {
        this.node = node;
        this.stabilizer = stabilizer;
        this.delay = delay;
    }

    @Override
    public void run() {
        while (this.node.getSettings().isActive()) {
            stabilizer.stabilize();
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                System.out.println("Exception while stabilizing: " + e);
            }
        }
    }
}
