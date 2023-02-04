package io.n2n.connection.reply;

import io.n2n.connection.Message;

/**
 * Doesn't receive any replies
 */
public class NoReply implements ReplyFilter {
    @Override
    public boolean isEnd(Message msg) {
        return true;
    }
}
