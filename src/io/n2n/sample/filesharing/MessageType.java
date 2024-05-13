package io.n2n.sample.filesharing;

public enum MessageType {
    NAME("NAME"), LIST("LIST"), JOIN("JOIN"), QUERY("QUER"),
    RESPONSE("RESP"), FILE_GET("FGET"), QUIT("QUIT"), REPLY("REPL"), ERROR("ERRO");

    private String name;

    MessageType(String name) {
        this.name = this.name();
    }

    public String getName() {
        return name;
    }
}
