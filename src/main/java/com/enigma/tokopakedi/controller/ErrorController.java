package com.enigma.tokopakedi.controller;

import com.enigma.tokopakedi.model.WebResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler({ResponseStatusException.class})
    public ResponseEntity<?> handlerStatusCode(ResponseStatusException e){
        WebResponse<String> webResponse = WebResponse.<String>builder()
                .status(e.getStatusCode().toString())
                .message(e.getReason())
                .build();
        return ResponseEntity.status(e.getStatusCode()).body(webResponse);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<?> handlerConstarintViolationException(ConstraintViolationException e){
        WebResponse<String> webResponse = WebResponse.<String>builder()
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(e.getLocalizedMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(webResponse);
    }
    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException e){
        HttpStatus httpStatus = null;
        String status = null;
        String message = e.getMostSpecificCause().getMessage();
        String reason = null;

        if (message.contains("foreign key constraint")){
            httpStatus = HttpStatus.BAD_REQUEST;
            status = HttpStatus.BAD_REQUEST.getReasonPhrase();
            reason = "cannot remove data";
        }else if(message.contains("unique constraint") || message.contains("Duplicate entry")){
            httpStatus = HttpStatus.CONFLICT;
            status = HttpStatus.CONFLICT.getReasonPhrase();
            reason = "Data Duplicate";
        }else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            status = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
            reason = "Internal Server Error";
        }

        WebResponse<String> webResponse = WebResponse.<String>builder()
                .status(status)
                .message(reason)
                .build();
        return ResponseEntity.status(httpStatus).body(webResponse);
    }
}
