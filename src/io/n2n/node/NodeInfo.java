package io.n2n.node;

import java.util.Objects;

/**
 * Maintains information of a node in the system, along with the node's
 * id, host(ip address or domain) and port.
 */
public class NodeInfo {
    private String id;
    private String host;
    private int port;

    /**
     * Initializes a new NodeInfo object.
     *
     * @param id   this node's unique identifier in the network
     * @param host the ip address
     * @param port the tcp port
     * @throws NullPointerException
     */
    public NodeInfo(String id, String host, int port) throws NullPointerException {
        if (id == null || host == null) {
            throw new NullPointerException("id and host must not be null");
        }
        this.id = id;
        this.host = host;
        this.port = port;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "NodeInfo{" +
                "id='" + id + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                '}';
    }

    /**
     * Checks id is equivalent or not.
     *
     * @param o the other object
     * @return true if id is equivalent or false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeInfo nodeInfo = (NodeInfo) o;
        return id.equals(nodeInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
