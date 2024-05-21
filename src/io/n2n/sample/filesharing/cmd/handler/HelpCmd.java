package io.n2n.sample.filesharing.cmd.handler;

import io.n2n.node.Node;
import io.n2n.sample.filesharing.cmd.CommandHandler;
import io.n2n.sample.filesharing.cmd.CommandListener;

import java.util.List;
import java.util.Map;

public class HelpCmd extends CommandHandler {
    private CommandListener listener;

    public HelpCmd(CommandListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCommand(Node sender, String label, String[] args, Map<String, List<String>> options) {
        String help = "[Available commands]\n";
        for (Map.Entry<String, CommandHandler> entry : listener.getHandlers().entrySet()) {
            help += entry.getKey() + " " + entry.getValue().helpMsg() + "\n";
        }

        // Display help message
        System.out.println(help);
    }

    @Override
    public String helpMsg() {
        return ": Display help message";
    }
}
