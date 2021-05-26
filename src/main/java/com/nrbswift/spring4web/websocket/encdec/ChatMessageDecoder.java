package com.nrbswift.spring4web.websocket.encdec;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.StringReader;

public class ChatMessageDecoder implements Decoder.Text<ChatMessage> {
    @Override
    public ChatMessage decode(String chatMessage) throws DecodeException {
        JsonObject jsonObject = Json.createReader(new StringReader(chatMessage)).readObject();

        ChatMessage decodeMessage = new ChatMessage();
        decodeMessage.setMessage(jsonObject.getString("message"));

        return decodeMessage;
    }

    @Override
    public boolean willDecode(String chatMessage) {
        boolean flag = true;

        try {
            Json.createReader(new StringReader(chatMessage)).readObject();
        }
        catch (Exception e) {
            flag = false;
        }

        return flag;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
