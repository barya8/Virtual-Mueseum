package com.company.dto;

public class ServiceResult {
    private ReturnCodeAndMessage serviceResult;

    public ServiceResult() {
    }

    public ReturnCodeAndMessage getServiceResult() {
        return serviceResult;
    }

    public ServiceResult setServiceResult(ReturnCodeAndMessage serviceResult) {
        this.serviceResult = serviceResult;
        return this;
    }
}
