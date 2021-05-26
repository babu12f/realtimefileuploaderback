package com.nrbswift.spring4web.websocket;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

public class HttpSessionConfigurator extends ServerEndpointConfig.Configurator {
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        System.out.println("modifyHandshake() Current thread " + Thread.currentThread().getName());
        sec.getUserProperties().put("id", request.getHttpSession());
        System.out.println("modifyHandshake() User " + "babor" + " with http session ID " + ((HttpSession) request.getHttpSession()).getId());
    }
}
