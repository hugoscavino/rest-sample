package com.candidate.test.product.controller;

import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for a use in constructing a useful ResponseEntity<Object>
 */
public final class BaseResponse<T> {

    public final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public String msg;
    public T body;

    /**
     * Read-only timestamp
     */
    private String timestamp;

    public BaseResponse(T body) {
        timestamp = formatter.format(LocalDateTime.now());
        this.body =body;
    }

    public ResponseEntity<Object> toResponseEntity(){
        return ResponseEntity.ok().body(this);
    };

    public ResponseEntity<Object> toBadRequestResponseEntity(){
        return ResponseEntity.badRequest().body(this);
    };

}
