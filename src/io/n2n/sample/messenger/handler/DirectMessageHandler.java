package io.n2n.sample.messenger.handler;

import io.n2n.connection.Connection;
import io.n2n.connection.Handler;
import io.n2n.connection.Message;
import io.n2n.sample.messenger.MessengerMsgType;
import io.n2n.sample.messenger.MessengerNode;

/**
 * A handler to get a direct message sent from a node.
 * <p>
 * Data format: [id] [message]+
 */
public class DirectMessageHandler implements Handler {
    @Override
    public void handle(Connection connection, Message msg) {
        String[] data = msg.getData().split(MessengerNode.DELIMITER);

        String id, message;
        try {
            // parse data
            id = data[0];
            message = msg.getData().substring(id.length() + 1);
        } catch (Exception e) {
            connection.sendData(new Message(MessengerMsgType.EROR.name(), "Error while parsing arguments"));
            return;
        }

        System.out.println("Direct message arrived from the node(" + id + ")");
        System.out.println(message);
    }

    @Override
    public String getName() {
        return MessengerMsgType.DMSG.name();
    }
}
