package com.company.dto;

public class ReturnCodeAndMessage {
    private String returnCode;
    private String returnMessage;

    //getters and setters

    public String getReturnCode() {
        return returnCode;
    }

    public ReturnCodeAndMessage setReturnCode(String returnCode) {
        this.returnCode = returnCode;
        return this;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public ReturnCodeAndMessage setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
        return this;
    }
}
