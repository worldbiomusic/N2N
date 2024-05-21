package io.n2n.sample.filesharing.cmd.handler;

import io.n2n.node.Node;
import io.n2n.sample.filesharing.cmd.CommandHandler;

import java.util.List;
import java.util.Map;

public class StartCmd extends CommandHandler {
    @Override
    public void onCommand(Node sender, String label, String[] args, Map<String, List<String>> options) {
        // must set the node to active before starting the listening socket and stabilizer
        sender.getSettings().setActive(true);

        // start listening socket and stabilizer
        sender.startListeningSocket();
        sender.startStabilizer(1000 * 60); // 1 minute
        System.out.println("Starting node... (listening on " + sender.getInfo()+")");
    }

    @Override
    public String helpMsg() {
        return ": Start the node";
    }
}
