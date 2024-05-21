package io.n2n.sample.filesharing.cmd.handler;

import io.n2n.node.Node;
import io.n2n.sample.filesharing.cmd.CommandHandler;

import java.util.List;
import java.util.Map;

public class StopCmd extends CommandHandler {
    @Override
    public void onCommand(Node sender, String label, String[] args, Map<String, List<String>> options) {
        sender.getSettings().setActive(false);
        System.out.println("Stopping node...");
    }

    @Override
    public String helpMsg() {
        return ": Stop the node";
    }
}
