# Messenger
Sends a message to the neighbors and manages them. (in development)

# Features
- Unstructured P2P network
- Using ping stabilizer and normal router

# Message types
- `NAME`: request id of the receiver by connecting with host and port
- `JOIN`: insert my info to another node
- `QUIT`: send a quit msg to let neighbors know with limited hops
- `FIND`: find a specific node with id with limited hops
- `LIST`: get list of nodes with limited hops
- `DMSG`: send a direct message to the specific node
- `BMSG`: broadcast a message to neighbors with limited hops
- `RPLY`: reply to sender using received connection
- `EROR`: notify an error to sender using received connection

[//]: # (# TODO)
[//]: # (- Remove `name&#40;&#41;` of Handler &#40;for lambda&#41;)
[//]: # (- Rename Handler to MessageHandler)