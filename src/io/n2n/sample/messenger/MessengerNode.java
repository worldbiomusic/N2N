package io.n2n.sample.messenger;

import io.n2n.node.Node;
import io.n2n.node.NodeInfo;
import io.n2n.sample.messenger.cmd.CommandManager;


public class MessengerNode extends Node {
    public static final String DELIMITER = "\\s";
    private CommandManager cmdExecutor;

    public MessengerNode(NodeInfo info) {
        super(info);
        this.cmdExecutor = new CommandManager(this);
    }

    public void join() {

    }
}
