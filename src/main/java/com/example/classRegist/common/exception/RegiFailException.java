package com.example.classRegist.common.exception;

public class RegiFailException extends RuntimeException{
    private final int ERROR_CODE;

    public RegiFailException(String msg, int errorCode) {
        super(msg);
        ERROR_CODE = errorCode;
    }
}
