package io.n2n.sample.filesharing.cmd;

import io.n2n.connection.Message;
import io.n2n.sample.messenger.MessengerMsgType;
import io.n2n.sample.messenger.MessengerNode;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private MessengerNode node;
    private Map<String, CommandExecutor> commands;

    public CommandManager(MessengerNode node) {
        this.node = node;
        this.commands = new HashMap<>();
    }

    public void run(String cmd) {
        // <label> <args>


    }

    private void sendData(String cmd) {
        String label = extractLabel(cmd);
        cmd = removeLabel(cmd);

        MessengerMsgType msgType;
        try {
            msgType = MessengerMsgType.valueOf(label);
        } catch (IllegalArgumentException e) {
            System.out.println("No such command. Type help for usage.");
            return;
        }

        String msgData = cmd;
        Message msg = new Message(msgType.name().toUpperCase(), msgData);

    }

    private String extractLabel(String cmd) {
        return cmd.split("\\s")[0];
    }

    /**
     * Removes first head of given command.
     *
     * @return command without head
     */
    private String removeLabel(String cmd) {
        return cmd.substring(extractLabel(cmd).length() + 1);
    }


}
