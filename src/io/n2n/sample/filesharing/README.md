# FileSharing
Shares some files in P2P. [Protocol designed by Nadeem Abdul Hamid. (Python version)](https://cs.berry.edu/~nhamid/p2p/filer-python.html)

# Features
- Unstructured P2P network
- Using ping stabilizer and normal router

# Message types
- `NAME`: Requests a peer to reply with its peer id.
- `LIST`: Requests a peer to reply with the peer's neighbor list.
- `JOIN <pid> <host> <port>`: Requests a peer to add a new peer to its list of known peers.
- `QUER <return-pid> <key> <ttl>`: Queries a peer to find a file with file matching key.
- `RESP <file-name> <pid>`: Notifies a peer that the node specified by pid has a file with the given name.
- `FGET <file-name>`: Request a peer to reply with the contents of the specified file.
- `QUIT <pid>`: Indicate to a peer that the node identified by pid wishes to be unregistered from the P2P system.
- `REPL`: Used to indicate an acknowledgement of the other message types above or to send back results of a successful request.
- `ERRO <msg>`: Used to indicate an erroneous or unsuccessful request.

# Commands
in writing...


# TODO
- change message data structure to json format