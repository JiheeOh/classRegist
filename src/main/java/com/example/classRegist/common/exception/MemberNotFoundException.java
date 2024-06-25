package com.example.classRegist.common.exception;

public class MemberNotFoundException extends RuntimeException{
    private final int ERROR_CODE;

    public MemberNotFoundException(String msg, int errorCode) {
        super(msg);
        ERROR_CODE = errorCode;
    }
}
