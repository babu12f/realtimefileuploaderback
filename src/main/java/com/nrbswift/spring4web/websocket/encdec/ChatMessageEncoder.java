package com.nrbswift.spring4web.websocket.encdec;

import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class ChatMessageEncoder implements Encoder.Text<ChatMessage> {
    @Override
    public String encode(ChatMessage chatMessage) throws EncodeException {
        return Json
                .createObjectBuilder()
                .add("name", chatMessage.getName())
                .add("message", chatMessage.getMessage())
                .build()
                .toString();
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
