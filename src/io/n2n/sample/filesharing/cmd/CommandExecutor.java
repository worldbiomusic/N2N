package io.n2n.sample.filesharing.cmd;

import io.n2n.node.Node;

public interface CommandExecutor {
    void onCommand(Node sender, String label, String[] args);
}
