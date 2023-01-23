package io.n2n.stabilizer;

/**
 * Interface for objects that can be used to stabilize a p2p
 * network, or that, in general, periodically invoked every few seconds.
 */
public interface Stabilizer {
    void stabilize();
}
