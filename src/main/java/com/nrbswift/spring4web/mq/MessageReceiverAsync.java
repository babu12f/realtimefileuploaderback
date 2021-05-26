package com.nrbswift.spring4web.mq;

import com.nrbswift.spring4web.fileprocess.FileProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

@Component
public class MessageReceiverAsync implements MessageListener {
    @Autowired
    MessageConverter messageConverter;

    @Autowired
    FileProcessor fileProcessor;

    @Override
    public void onMessage(Message message) {
        try {
            MqMessageObject mqMessageObject = (MqMessageObject) messageConverter.fromMessage(message);
            System.out.println("======================== Message Receive ===========================");
            System.out.println(mqMessageObject.getFileList());
            fileProcessor.processFileAndSendSocket(mqMessageObject);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
