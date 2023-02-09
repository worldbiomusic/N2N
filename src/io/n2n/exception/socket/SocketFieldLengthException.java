package io.n2n.exception.socket;

import java.net.SocketException;

public class SocketFieldLengthException extends SocketException {
    public SocketFieldLengthException(String msg) {
        super(msg);
    }
}
