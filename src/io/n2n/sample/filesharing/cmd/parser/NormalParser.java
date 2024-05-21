package io.n2n.sample.filesharing.cmd.parser;

import io.n2n.sample.filesharing.cmd.CommandHandler;
import io.n2n.sample.filesharing.cmd.CommandParser;
import io.n2n.sample.filesharing.cmd.handler.MessageCmd;

import java.util.*;

/**
 * NormalParser is a class for parsing command with normal style. <br>
 * Example: <code>msg -t 1 -m "Hello, World!"</code>
 */
public class NormalParser implements CommandParser {
    public static void main(String[] args) {
        CommandParser parser = new NormalParser();
        CommandHandler handler = new MessageCmd();
        String cmd = "msg -t join -n testid myid 25555";
        System.out.println("cmd: " + cmd);
        System.out.println("label: " + parser.label(cmd));
        System.out.println("args: " + Arrays.toString(parser.args(handler, cmd)));
        System.out.println("options: " + parser.options(handler, cmd));
    }

    @Override
    public String label(String cmd) {
        return cmd.split("\\s")[0];
    }

    @Override
    public String[] args(CommandHandler handler, String cmd) {
        // remove label
        cmd = cmd.replaceFirst(label(cmd), "").trim();

        List<String> args = new ArrayList<>();
        Stack<String> tokens = new Stack<>();
        List<String> strTokens = Arrays.asList(cmd.split(" "));
        Collections.reverse(strTokens);
        tokens.addAll(strTokens);
        Map<String, Integer> optionCount = handler.options();

        while (!tokens.isEmpty()) {
            String token = tokens.pop();
            if (token.startsWith("-")) {
                // option args
                int argsCount = optionCount.get(token);
                while (argsCount > 0) {
                    tokens.pop();
                    argsCount--;
                }
            } else {
                // not option args
                args.add(token);
            }
        }
        return args.toArray(new String[0]);
    }

    @Override
    public Map<String, List<String>> options(CommandHandler handler, String cmd) {
        // remove label
        cmd = cmd.replaceFirst(label(cmd), "").trim();

        Map<String, List<String>> options = new HashMap<>();
        options.put("#", new ArrayList<>()); // not option args
        Stack<String> tokens = new Stack<>();
        tokens.addAll(Arrays.asList(cmd.split(" ")));
        Collections.reverse(tokens);
        Map<String, Integer> optionCount = handler.options();

        while (!tokens.isEmpty()) {
            String token = tokens.pop();
            if (token.startsWith("-")) {
                // option args
                int argsCount = optionCount.getOrDefault(token, -1);
                if (argsCount == -1) {
                    // invalid option
                    throw new IllegalArgumentException("Invalid option: " + token);
                }

                List<String> args = new ArrayList<>();
                while (argsCount > 0) {
                    args.add(tokens.pop());
                    argsCount--;
                }
                options.put(token, args);
            } else {
                // not option args
                options.get("#").add(token);
            }
        }
        return options;
    }
}
