package io.n2n.connection.reply;

import io.n2n.connection.Message;
import io.n2n.node.Node;
import io.n2n.node.NodeInfo;

/**
 * Checks conditions to get more replies or not from a receiver in {@link Node#sendData}.
 */
public interface ReplyCheck {
    /**
     * Returns true if want to stop getting more replies.
     *
     * @param msg New reply
     * @return True if want to stop getting more replies or False
     */
    boolean isEnd(Message msg);
}
