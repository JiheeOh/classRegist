package com.example.classRegist.common.exception;

public class LectureNotFoundException extends RuntimeException{
    private final int ERROR_CODE;

    public LectureNotFoundException(String msg, int errorCode) {
        super(msg);
        ERROR_CODE = errorCode;
    }
}
