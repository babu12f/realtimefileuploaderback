package com.nrbswift.spring4web.mq;

import com.nrbswift.spring4web.dao.Picture;

import java.io.Serializable;

public class MqMessageObject implements Serializable {
    private String userSessionId;

    private String fileList;

    public String getUserSessionId() {
        return userSessionId;
    }

    public void setUserSessionId(String userSessionId) {
        this.userSessionId = userSessionId;
    }

    public String getFileList() {
        return fileList;
    }

    public void setFileList(String fileList) {
        this.fileList = fileList;
    }
}
