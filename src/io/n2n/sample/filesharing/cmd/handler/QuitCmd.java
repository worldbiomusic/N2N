package io.n2n.sample.filesharing.cmd.handler;

import io.n2n.connection.Message;
import io.n2n.node.Node;
import io.n2n.sample.filesharing.MessageType;
import io.n2n.sample.filesharing.cmd.CommandHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Quit command handler <br>
 * Usage: <code>quit <id>+ </> [-a]</code>
 */
public class QuitCmd extends CommandHandler {
    private Node node;

    @Override
    protected void addOptions() {
        super.addOptions();
        this.options.put("-a", 0);
    }

    public QuitCmd(Node node) {
        this.node = node;
    }

    @Override
    public void onCommand(Node sender, String label, String[] args, Map<String, List<String>> options) {
        String pid = this.node.getInfo().getId();
        Message msg = new Message(MessageType.QUIT.name(), pid);

        List<String> peers = new ArrayList<>();
        if (options.containsKey("-a")) {
            // get all peers
            this.node.getSettings().getNeighborManager().getNodes().forEach(n -> {
                peers.add(n.getId());
            });
        } else {
            // add args peers
            peers.addAll(Arrays.asList(args));
        }

        // send quit message to peers
        peers.forEach(peer -> new Thread(() -> quit(peer, msg)).start()); // thread
    }

    private void quit(String peer, Message msg) {
        Message reply = this.node.sendData(peer, msg).stream().findFirst().get();
        System.out.println("[QuitHandler] " + reply.getType() + ": " + reply.getData());
    }

    @Override
    public String helpMsg() {
        return "";
    }
}
