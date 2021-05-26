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

    <form action="" id="myform">
        <textarea id="messageTextarea" rows="20" cols="50"></textarea> <br>
        <input type="text" id="messageText" size="50"/>
        <input type="button" value="send" onclick="sendMessage()"/><br>
    </form>

    <script type="text/javascript">
        document.getElementById("myform").addEventListener("submit", submitform);
        var ws = new WebSocket("ws://localhost:8080/spring4web/chatendpoint2");
        ws.onmessage = function (message) {
            var msgJson = JSON.parse(message.data);

            if (msgJson.message != null) {
                messageTextarea.value += msgJson.name + " : " + msgJson.message + "\n";
            }
        };

        function sendMessage() {
            ws.send(JSON.stringify({'message' : messageText.value}));
            messageText.value = "";
        }

        function submitform(e) {
            e.preventDefault();
            sendMessage();
        }

        window.onbeforeunload = function () {
            ws.onclose = function () {};
            ws.close();
        }

    </script>

</body>
</html>