package io.n2n.sample.filesharing;

import io.n2n.node.Node;
import io.n2n.node.NodeInfo;
import io.n2n.sample.filesharing.cmd.CommandListener;
import io.n2n.sample.filesharing.cmd.handler.MessageCmd;
import io.n2n.sample.filesharing.cmd.parser.NormalParser;

public class FileSharingApp {
    public static void main(String[] args) {
        new FileSharingApp();
    }

    public FileSharingApp() {
        System.out.println("[ FileSharingApp ]");

        Node node = new FSNode(new NodeInfo("fs1", "localhost", 11111));
        CommandListener cmdListener = new CommandListener(new NormalParser());
        registerCommands(cmdListener);

        while (true) {
            String cmd = System.console().readLine();
            cmdListener.run(node, cmd);
        }
    }

    private void registerCommands(CommandListener cmdManager) {
        cmdManager.registerCommand("msg", new MessageCmd());
    }
}
