package io.n2n.sample.filesharing.cmd;

import io.n2n.node.Node;

import java.util.*;

/**
 * CommandListener is a class for listening events and executing command handler.
 */
public class CommandListener {
    private Map<String, CommandHandler> handlers;
    private CommandParser parser;

    public CommandListener(CommandParser parser) {
        this.handlers = new HashMap<>();
        this.parser = parser;
    }

    public CommandHandler registerCommand(String label, CommandHandler handler) {
        return handlers.put(label, handler);
    }

    public CommandHandler unregisterCommand(String label) {
        return handlers.remove(label);
    }

    public void run(Node node, String cmd) {
        String label = this.parser.label(cmd);
        CommandHandler handler = handlers.get(label);
        Map<String, List<String>> options = this.parser.options(handler, removeLabel(cmd));
        List<String> normalArgs = options.remove("#");
        handler.onCommand(node, label, normalArgs.toArray(new String[0]), options);
    }

    /**
     * Removes first head of given command.
     *
     * @return command without head
     */
    private String removeLabel(String cmd) {
        return cmd.substring(this.parser.label(cmd).length() + 1);
    }

    public Map<String, CommandHandler> getHandlers() {
        return handlers;
    }
}
