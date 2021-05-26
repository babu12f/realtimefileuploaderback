package com.nrbswift.spring4web.websocket;

import org.springframework.stereotype.Component;

import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
@ServerEndpoint(value = "/chatendpoint", configurator = HttpSessionConfigurator.class)
public class MyChatEndpoint {

    static Map<String, Session> chatRoomUsers = Collections.synchronizedMap(new HashMap<>());
    //Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        chatRoomUsers.put(((HttpSession) config.getUserProperties().get("id")).getId(), session);
    }

    public void sendMessageToUser(String userId, String message) {
        Session session = chatRoomUsers.get(userId);
        if (session != null) {
            try {
                session.getBasicRemote().sendText(buildJsonData("fileUpload", message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//    @OnOpen
//    public void opened(@PathParam("user") String user, Session session, EndpointConfig config) throws IOException{
//        System.out.println("opened() Current thread "+ Thread.currentThread().getName());
//        this.httpSession = (HttpSession) config.getUserProperties().get(user);
//        System.out.println("User joined "+ user + " with http session id "+ httpSession.getId());
//        String response = "User " + user + " | WebSocket session ID "+ session.getId() +" | HTTP session ID " + httpSession.getId();
//        System.out.println(response);
//        session.getBasicRemote().sendText(response);
//    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        String userName = (String) session.getUserProperties().get("username");
        if (userName == null) {
            session.getUserProperties().put("username", message);
            session.getBasicRemote().sendText(buildJsonData("system", "you are connected as " + message));
        }
        else {
            //Iterator<Session> iterator = chatRoomUsers.iterator();
//            while (iterator.hasNext()) {
//                iterator.next().getBasicRemote().sendText(buildJsonData(userName, message));
//            }
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        chatRoomUsers.remove(session);
    }

    @OnError
    public void onError(Session session, Throwable thr) {
    }

    private String buildJsonData(String username, String msg) {
        JsonObject jsonObject = Json.createObjectBuilder().add("message", username + " : "+msg).build();

//        StringWriter stringWriter = new StringWriter();
//
//        try {
//            JsonWriter jsonWriter = Json.createWriter(stringWriter);
//            jsonWriter.write(jsonObject);
//            return stringWriter.toString();
//        }
//        catch (Exception e) {
//            System.out.println("Exception +" +e.getMessage());
//        }
//        return stringWriter.toString();
        return jsonObject.toString();
    }
}
