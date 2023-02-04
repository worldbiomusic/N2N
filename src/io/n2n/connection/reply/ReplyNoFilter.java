package io.n2n.connection.reply;

import io.n2n.connection.Message;

/**
 * Returns always false to proceed the receiving remaining replies.
 */
public class ReplyNoFilter implements ReplyFilter {
    @Override
    public boolean isEnd(Message msg) {
        return false;
    }
}