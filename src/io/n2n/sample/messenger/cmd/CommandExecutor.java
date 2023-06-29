package io.n2n.sample.messenger.cmd;

import io.n2n.node.Node;

public interface CommandExecutor {
    void onCommand(Node sender, String label, String[] args);
}
