package io.n2n.connection.reply;

import io.n2n.connection.Message;

/**
 * Checks a reply whether it contains a specific world or not implementing ReplyFilter.
 */
public class ReplyWordFilter implements ReplyFilter {
    private String word;

    public ReplyWordFilter(String word) {
        this.word = word;
    }

    @Override
    public boolean isEnd(Message msg) {
        return msg.getData().contains(this.word);
    }
}
