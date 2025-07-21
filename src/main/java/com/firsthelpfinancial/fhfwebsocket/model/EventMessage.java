package com.firsthelpfinancial.fhfwebsocket.model;

public class EventMessage {
    private String appId;
    private String action;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}

