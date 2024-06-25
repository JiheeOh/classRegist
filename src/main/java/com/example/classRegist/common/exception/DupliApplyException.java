package com.example.classRegist.common.exception;

public class DupliApplyException extends RuntimeException{
    private final int ERROR_CODE;

    public DupliApplyException(String msg, int errorCode) {
        super(msg);
        ERROR_CODE = errorCode;
    }
}
