import Stomp from 'stompjs';

let stompClient = null;
const socketUrl = 'ws://localhost:9000/secured/app'; // Ensure it's ws:// for WebSocket
let sessionId = "";

export default {
    connect(token, onConnect, onError) {
        const socket = new WebSocket(socketUrl); // Create a WebSocket connection

        stompClient = Stomp.over(socket);

        stompClient.connect({ Authorization: `Bearer ${token}` }, frame => {
            // Assuming the URL pattern matches your server's WebSocket setup
            console.log(stompClient.ws.url);
            let url = stompClient.ws.url;
            url = url.replace("ws://localhost:9000/secured/app/", "");
            sessionId = url.split('/')[0]; // Extract sessionId from URL
            console.log("Your current session is: " + sessionId);
            onConnect();
        }, error => {
            console.error("Connection error: ", error);
            if (onError) {
                onError(error);
            }
        });
    },

    subscribeToChat(onMessage) {
        alert("subscribing")
        const destination = `/secured/user/queue/specific-user`;
        stompClient.subscribe('/user/secured/user/queue/specific-user', function(message) {
            alert("recieved")
            const receivedMessage = JSON.parse(message.body);
            onMessage(receivedMessage)
            // Handle the received message
        });
        
    },

    subscribe(destination, onMessage, user) {
        if (!stompClient || !stompClient.connected) {
            console.error('Cannot subscribe: WebSocket is not connected');
            return;
        }
        console.log(destination)
        stompClient.subscribe(`${destination}`, message => {
            if (onMessage) {
                onMessage(JSON.parse(message.body)); // Parse the JSON message
            }
        });
    },

    sendChatMessage(message) {
        const destination = '/app/secured/chat';
        this.send(destination, message);
    },

    send(destination, body) {
        if (!stompClient || !stompClient.connected) {
            console.error('Cannot send message: WebSocket is not connected');
            return;
        }
        stompClient.send(destination, {}, JSON.stringify(body)); // Send message
    },

    disconnect() {
        if (stompClient) {
            stompClient.disconnect();
            console.log('Disconnected');
        }
    }
}
