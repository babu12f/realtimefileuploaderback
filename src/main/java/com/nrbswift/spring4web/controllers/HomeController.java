package com.nrbswift.spring4web.controllers;

import com.google.gson.Gson;
import com.nrbswift.spring4web.dao.Picture;
import com.nrbswift.spring4web.mq.MessageReceiver;
import com.nrbswift.spring4web.mq.MessageSender;
import com.nrbswift.spring4web.mq.MqMessageObject;
import com.nrbswift.spring4web.utils.WebContextHolderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.PipedReader;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
public class HomeController {
    @Autowired
    MessageSender messageSender;

    @Autowired
    MessageReceiver messageReceiver;

    @RequestMapping("/")
    public String showHome() {
        return "homeReact";
    }

    @RequestMapping("/chat")
    public String showChat() {
        return "chat";
    }

    @RequestMapping("/chat2")
    public String showChat2() {
        return "chat2";
    }

    @RequestMapping("/send")
    @ResponseBody
    public String sendMessageToQueue(@RequestParam(value = "msg", required = false) String message) {
        if (message == null) {
            messageSender.sendMessage("any message");
        }
        else {
            messageSender.sendMessage(message);
        }

        return "message add Successfully :)";
    }
    @RequestMapping("/receive")
    @ResponseBody
    public String receiveMessageToQueue() {
        String message = messageReceiver.receiveMessage();

        return "message from queue : >>>> " + message;
    }

    @RequestMapping("/sendobj")
    @ResponseBody
    public String sendObjToQueue(@RequestParam(value = "pname", required = false) String productName) {

        List<Picture> list = Arrays.asList(
                new Picture(1, "path", new Date()),
                new Picture(1, "path", new Date())
        );

        Gson gson = new Gson();
        String picJson = gson.toJson(list);

        MqMessageObject mqMessageObject = new MqMessageObject();
        mqMessageObject.setFileList(picJson);
        mqMessageObject.setUserSessionId(WebContextHolderUtils.get().getSession().getId());

        messageSender.sendObjectMessage(mqMessageObject);

        return "message add Successfully :)";
    }
    @RequestMapping("/rcvobj")
    @ResponseBody
    public String receiveObjMessageToQueue() {
        MqMessageObject mqMessageObject = messageReceiver.receiveObjectMessage();

        return "message from queue : >>>> " + mqMessageObject.getFileList();
    }
}
