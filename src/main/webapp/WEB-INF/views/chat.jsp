<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Spring Web</title>
    <link href="<c:url value='/'/>static/css/style.css" rel="stylesheet" type="text/css"/>
</head>
<body>
    <h1>Running spring app form jsp</h1>

    <form action="">
        <textarea id="messageTextarea" rows="20" cols="50"></textarea> <br>
        <input type="text" id="messageText" size="50"/>
        <input type="button" value="send" onclick="sendMessage()"/><br>
    </form>

    <script type="text/javascript">
        var ws = new WebSocket("ws://localhost:8080/spring4web/chatendpoint");
        ws.onmessage = function (message) {
            var msgJson = JSON.parse(message.data);

            if (msgJson.message != null) {
                messageTextarea.value += msgJson.message + "\n";
            }
        };

        function sendMessage() {
            ws.send(messageText.value);
            messageText.value = "";
        }

    </script>

</body>
</html>