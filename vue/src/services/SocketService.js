import Stomp from 'stompjs';

let stompClient = null;
const socketUrl = 'http://localhost:9000/secured/app'; // Your WebSocket endpoint

export default {
    connect(token, onConnect, onError) {
        const webSocket = new WebSocket(socketUrl); // Create a WebSocket connection

        stompClient = Stomp.over(webSocket); // Wrap the WebSocket connection with Stomp

        // Connect with the authorization header
        stompClient.connect(
            { Authorization: `Bearer ${token}` }, // Set the auth header
            (frame) => {
                console.log('Connected: ' + frame);
                if (onConnect) onConnect(frame);
            },
            (error) => {
                console.error('Error connecting: ' + error);
                if (onError) onError(error);
            }
        );
    },

    subscribeToChat(callback) {
        const destination = '/secured/user/queue/specific-user';
        this.subscribe(destination, callback);
    },

    subscribe(destination, callback) {
        if (!stompClient || !stompClient.connected) {
            console.error('Cannot subscribe: WebSocket is not connected');
            return;
        }
        stompClient.subscribe(destination, (message) => {
            callback(JSON.parse(message.body));
        });
    },

    sendChatMessage(message) {
        alert("message sent")
        const destination = '/secured/chat';
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
