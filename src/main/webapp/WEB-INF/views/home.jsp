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
        <input type="text" id="command"/> <input type="button" value="send" onclick="sendMessage()"/><br>
        <textarea id="message" rows="20" cols="100"></textarea> <br>
    </form>

    <script type="text/javascript">
        var ws = new WebSocket("ws://localhost:8080/spring4web/babor");
        var msgBox = document.getElementById("message");
        ws.onopen = function (message) {
            msgBox.value += "server connected :) :)" + "\n";
        };
        ws.onclose = function (message) {
            ws.send("client disconnect");
            msgBox.value += "client disconnect :( **" + "\n";
        };
        ws.onerror = function (message) {
            msgBox.value += "something Error !!!...." + "\n";
        };
        ws.onmessage = function (message) {
            msgBox.value += "receive form server ====>> " + message.data + "\n";
        };

        function sendMessage() {
            if (command.value != "exit") {
                ws.send(command.value);
                msgBox.value += "send to server ===>" + command.value + "\n";
                command.value = "";
            }
            else {
                ws.close();
            }
        }
    </script>

</body>
</html>