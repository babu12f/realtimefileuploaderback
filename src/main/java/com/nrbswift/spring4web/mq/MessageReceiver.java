package com.nrbswift.spring4web.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;

@Component
public class MessageReceiver {
    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    MessageConverter messageConverter;

    public String receiveMessage() {
        try {
            Message message = jmsTemplate.receive();
            String response = (String) messageConverter.fromMessage(message);
            return response;
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return null;
    }

    public MqMessageObject receiveObjectMessage() {
        try {
            Message message = jmsTemplate.receive();
            MqMessageObject mqMessageObject = (MqMessageObject) messageConverter.fromMessage(message);
            return mqMessageObject;
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return null;
    }
}
