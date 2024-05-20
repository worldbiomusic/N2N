package io.n2n.sample.filesharing.cmd;

import java.util.List;
import java.util.Map;

/**
 * CommandParser is an interface for parsing command.
 */
public interface CommandParser {
    /**
     * Extracts label from given command.
     *
     * @param cmd command
     * @return label
     */
    public String label(String cmd);

    /**
     * Extracts normal arguments from given command. <br>
     * Example: <code>msg -t 1 hello world</code> <br>
     * if t is option which requires 1 argument, then <code>hello world</code> is normal arguments.
     *
     * @param handler command handler
     * @param cmd     command
     * @return arguments
     */
    public String[] args(CommandHandler handler, String cmd);

    /**
     * Extracts options from given command. <br>
     *
     * @param handler command handler
     * @param cmd command
     * @return options
     */
    public Map<String, List<String>> options(CommandHandler handler, String cmd);
}
