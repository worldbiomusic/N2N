package io.n2n.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Config {
    public static int MSG_TYPE_FIELD_LENGTH = 4;
    public static int MSG_DATA_FIELD_LENGTH = 4;
    public static Charset CHARSET = StandardCharsets.UTF_8;

    /**
     * @see {@link java.net.Socket#setSoTimeout(int)}
     */
    public static int SOCKET_READ_TIMEOUT = 0; // millisec

    /**
     * @see {@link java.net.ServerSocket#setSoTimeout(int)}
     */
    public static int SOCKET_CONNECTION_TIMEOUT = 1000 * 3; // millisec
}