package io.n2n.connection.reply;

import io.n2n.connection.Message;

/**
 * Checks a reply whether it contains a specific world or not implementing ReplyCheck.
 */
public class ReplyWordCheck implements ReplyCheck{
    private String word;

    public ReplyWordCheck(String word) {
        this.word = word;
    }

    @Override
    public boolean isEnd(Message msg) {
        return msg.getData().contains(this.word);
    }
}
