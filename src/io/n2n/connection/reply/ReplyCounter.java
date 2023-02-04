package io.n2n.connection.reply;

import io.n2n.connection.Message;

/**
 * Checks a reply with a counter implementing ReplyFilter.
 */
public class ReplyCounter implements ReplyFilter {
    private int max;
    private int count;

    public ReplyCounter(int max) {
        this.max = max;
        this.count = 0;
    }

    @Override
    public boolean isEnd(Message msg) {
        // increase count
        this.count++;

        return this.count >= this.max;
    }
}
