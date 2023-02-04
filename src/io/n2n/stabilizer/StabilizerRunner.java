package io.n2n.stabilizer;

/**
 * A class for stabilizing at specific intervals.
 */
public class StabilizerRunner extends Thread {
    private Stabilizer stabilizer;
    private int delay;

    /**
     * Constructs an object.
     *
     * @param stabilizer the stabilizer to use
     * @param delay      the delay (in milliseconds)
     */
    public StabilizerRunner(Stabilizer stabilizer, int delay) {
        this.stabilizer = stabilizer;
        this.delay = delay;
    }

    @Override
    public void run() {
        while (true) {
            stabilizer.stabilize();
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                System.out.println("Exception while stabilizing: " + e);
            }
        }
    }
}
