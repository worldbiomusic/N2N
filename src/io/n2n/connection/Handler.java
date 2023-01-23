package io.n2n.connection;

import io.n2n.connection.Connection;

/**
 * Handles an incoming connection from another node.
 * {@link Connection} can be used to send a reply to a sender and get data also.
 * Implementation can ignore connection by reading a type of the data if needed.
 * The handler is carried out by the {@link Dispatcher} registered to a Node.
 */
public interface Handler {
    void handle(Connection connection);
}
