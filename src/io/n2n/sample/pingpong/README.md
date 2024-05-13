# Ping pong
Sends a ping message and gets a pong message from the receiver node.

# Features
- Unstructured P2P network

# commands
- `init <id> <host> <port>`: initialize the node
- `ping <host> <port>`: send a ping message to host:port
- `status`: print status
- `exit`: exit the program

# Images
1. Initialize a new node (id: "a", host: "localhost", port: "10000").
![1](https://user-images.githubusercontent.com/61288262/216357774-38309a77-5a14-483b-9f97-e701f6482845.PNG)

2. Initialize another node (id: "b", host: "localhost", port: "10001").
![2](https://user-images.githubusercontent.com/61288262/216357788-fcf1ee3a-8788-4cf9-b5fe-29026a940673.PNG)

3. Send a ping to node "b" from "a".
![3](https://user-images.githubusercontent.com/61288262/216360331-15c6d1c0-5a4e-4c6f-af4c-99ab928d76de.PNG)

4. "b" received a pong from "a".
![4](https://user-images.githubusercontent.com/61288262/216357805-f789f09e-f991-4558-b0a7-2b58413f20c4.PNG)

