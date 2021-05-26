package com.nrbswift.spring4web.websocket;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/babor")
public class MyWebSocketEndpoint {
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("new client connected thrown traditional websocket .....");
    }

    @OnMessage
    public String onMessage(Session session, String message) {
        String clientMessage = message;
        System.out.println("message from client " + clientMessage);
        System.out.println("reply to client Hi :::)))" + clientMessage);
        return ("reply to client Hi :::)))" + clientMessage);
    }
    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println("new client close thrown traditional websocket .....");
    }

    @OnError
    public void onError(Session session, Throwable thr) {
        System.out.println("new client Error thrown traditional websocket .....");
    }
}
