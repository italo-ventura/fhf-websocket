const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/ws',
    reconnectDelay: 0
});

stompClient.onConnect = (frame) => {
    appId = $("#connect-app-id").val()
    stompClient.subscribe(`/topic/${appId}/events`, (message) => {
        console.log("message: " + JSON.parse(message.body).type)
        console.log('Connected: ' + frame);
        showMessage(JSON.parse(message.body).message);
    });
};


stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    setConnected(false);
    stompClient.deactivate();
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#services-called").show();
    } else {
        $("#services-called").hide();
    }
    $("#services").html("");
}

function connect() {
    const appId = $("#connect-app-id").val();

    fetch('http://localhost:8081/event', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({appId: appId})
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error calling endpoint /event');
            }
            return response.json();
        })
        .then(data => {
            stompClient.activate();
            setConnected(true);
        })
        .catch(error => {
            console.error('Connection failed:', error);
        });
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function showMessage(message) {
    $("#services").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $("#connect").click(() => connect());
    $("#disconnect").click(() => disconnect());
});