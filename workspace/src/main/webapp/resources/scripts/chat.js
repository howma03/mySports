// var userName = window.prompt("Enter your name", "some user");

function appendMessage(message) {
    $('#messages').append($('<div />').text(message.from + ": " + message.message))
}

function getPreviousMessages() {
    $.get('/api/chat/read').done(function (messages) {
        messages.forEach(appendMessage);
    });
}

function sendMessage() {
    var $messageInput = $('#messageInput');
    var message = {message: $messageInput.val(), from: "Mark"};
    $messageInput.val('');
    $.post('/api/chat/send', message);
}

function onNewMessage(result) {
    var message = JSON.parse(result.body);
    appendMessage(message);
}

function clearMessages() {
    $('#messages').html("");
}

function connectWebSocket() {
    var socket = new SockJS('/chatWS');
    stompClient = Stomp.over(socket);
    //stompClient.debug = null;
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/messages', onNewMessage);
    });
}

getPreviousMessages();
connectWebSocket();