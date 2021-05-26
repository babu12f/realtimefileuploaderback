package com.nrbswift.spring4web.websocket.encdec;

import com.nrbswift.spring4web.websocket.encdec.ChatMessage;
import com.nrbswift.spring4web.websocket.encdec.ChatMessageDecoder;
import com.nrbswift.spring4web.websocket.encdec.ChatMessageEncoder;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@ServerEndpoint(value = "/chatendpoint2", encoders = {ChatMessageEncoder.class}, decoders = {ChatMessageDecoder.class})
public class MyChatEndPoint2 {
    static Set<Session> chatRoomUsers = Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void onOpen(Session session) {
        chatRoomUsers.add(session);
    }

    @OnMessage
    public void onMessage(ChatMessage inMessage, Session session) throws IOException, EncodeException {
        String userName = (String) session.getUserProperties().get("username");
        ChatMessage outMessage = new ChatMessage();
        if (userName == null) {
            session.getUserProperties().put("username", inMessage.getMessage());
            outMessage.setName("System");
            outMessage.setMessage("you are connected as " + inMessage.getMessage());
            session.getBasicRemote().sendObject(outMessage);
        }
        else {
            Iterator<Session> iterator = chatRoomUsers.iterator();
            outMessage.setName(userName);
            outMessage.setMessage(inMessage.getMessage());
            while (iterator.hasNext()) {
                iterator.next().getBasicRemote().sendObject(outMessage);
            }
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        chatRoomUsers.remove(session);
    }
}
