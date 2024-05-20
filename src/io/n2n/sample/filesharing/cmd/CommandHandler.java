package io.n2n.sample.filesharing.cmd;

import io.n2n.node.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CommandHandler is a class for handling command.
 */
public abstract class CommandHandler {

    protected Map<String, Integer> options;

    public CommandHandler() {
        this.options = new HashMap<>();
        addOptions();
    }


    /**
     * Executes when the command is called.
     *
     * @param sender  Node that called the command
     * @param label   Command label
     * @param args    Normal arguments (without options)
     * @param options Options with their values
     */
    public abstract void onCommand(Node sender, String label, String[] args, Map<String, List<String>> options);

    /**
     * Adds options to the command. (Option prefix is - or --)
     *
     * <code>Example: msg -n <id> -m <type><data></code>
     */
    protected void addOptions() {
    }

    public Map<String, Integer> options() {
        return options;
    }
}
