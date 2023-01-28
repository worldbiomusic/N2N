package io.n2n.node;

import io.n2n.connection.Connection;
import io.n2n.connection.Message;
import io.n2n.connection.reply.ReplyCheck;
import io.n2n.connection.reply.ReplyNoCheck;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A primary class for N2N(Node to Node) system. It has node's information, dispatch
 * connections using handlers, manage neighbors, stabilize the network and
 * has a router.
 */
public class Node {
    private NodeInfo info;
    private NodeSettings settings;

    public List<Message> sendData(NodeInfo node, Message msg) {
        return sendData(node, msg, new ReplyNoCheck());
    }

    public List<Message> sendData(NodeInfo node, Message msg, ReplyCheck replyCheck) {
        List<Message> replies = new ArrayList<>();

        // create a connection
        try (Connection connection = new Connection(node, msg)) {
            connection.send();

            // receive replies from the receiver
            Message reply = connection.receive();
            while (reply != null && !replyCheck.isEnd(reply)) {
                replies.add(reply);
                reply = connection.receive();
            }
        } catch (IOException e) {
            System.out.println("Error while sending data to " + node + ": " + e);
        }
        return replies;
    }
}
