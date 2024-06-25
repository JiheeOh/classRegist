package com.example.classRegist.presenter;

import com.example.classRegist.common.exception.DupliApplyException;
import com.example.classRegist.common.exception.LectureNotFoundException;
import com.example.classRegist.common.exception.MemberNotFoundException;
import com.example.classRegist.common.exception.RegiFailException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
class ApiControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(Exception e) {
        return ResponseEntity.status(500).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,e.toString()));
    }

    @ExceptionHandler(value = RegiFailException.class)
    public ResponseEntity<ErrorResponse> handleRegiFailException(Exception e) {
        return ResponseEntity.status(500).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,e.toString()));
    }

    @ExceptionHandler(value = DupliApplyException.class)
    public ResponseEntity<ErrorResponse> handleDupliApplyException(Exception e) {
        return ResponseEntity.status(500).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,e.toString()));
    }


    @ExceptionHandler(value = LectureNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLectureNotFoundException(Exception e) {
        return ResponseEntity.status(500).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,e.toString()));
    }

    @ExceptionHandler(value = MemberNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMemberNotFoundException(Exception e) {
        return ResponseEntity.status(500).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,e.toString()));
    }



}
