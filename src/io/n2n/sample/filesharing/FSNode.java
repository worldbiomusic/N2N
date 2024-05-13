package io.n2n.sample.filesharing;

import io.n2n.connection.Handler;
import io.n2n.node.Node;
import io.n2n.node.NodeInfo;
import io.n2n.router.NormalRouter;
import io.n2n.sample.filesharing.handler.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * File sharing node
 */
public class FSNode extends Node {
    private Map<String, NodeInfo> files;
    public FSNode(NodeInfo info) {
        super(info);

        // settings
        this.files = new ConcurrentHashMap<>();

        // handlers
        List<Handler> handlers = List.of(
                new NameHandler(this),
                new ListHandler(this),
                new JoinHandler(this),
                new QueryHandler(this),
                new ResponseHandler(this),
                new FileGetHandler(this),
                new QuitHandler(this),
                new ReplyHandler(this),
                new ErrorHandler(this)
        );
        handlers.forEach(this.getSettings().getDispatcher()::addHandler);

        // router
        this.getSettings().setRouter(new NormalRouter(this));
    }

    public Map<String, NodeInfo> getFiles() {
        return files;
    }
}
