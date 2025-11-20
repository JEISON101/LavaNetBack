package com.lavanet.lavanet_api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<T> {

    private boolean success;
    private String message;
    private T data;
    private String error;
    private String token;

    // Constructor success
    public ResponseDto(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // Contructor error
    public ResponseDto(boolean success, String message, T data, String error) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.error = error;
    }

    // Constructor con token
    public ResponseDto(boolean success, String message, T data, String error, String token) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.error = error;
        this.token = token;
    }
}
