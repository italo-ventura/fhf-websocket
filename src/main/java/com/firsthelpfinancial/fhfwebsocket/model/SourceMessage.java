package com.firsthelpfinancial.fhfwebsocket.model;

public class SourceMessage {
    private String message;

    public SourceMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
