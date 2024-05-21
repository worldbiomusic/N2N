package io.n2n.sample.filesharing.cmd.handler;

import io.n2n.node.Node;
import io.n2n.sample.filesharing.cmd.CommandHandler;

import java.util.List;
import java.util.Map;

public class ExitCmd extends CommandHandler {
    @Override
    public void onCommand(Node sender, String label, String[] args, Map<String, List<String>> options) {
        sender.getSettings().setActive(false);
        System.out.println("Bye!");

        // shutdown the program
        System.exit(0);
    }

    @Override
    public String helpMsg() {
        return ": Exit the program";
    }
}
