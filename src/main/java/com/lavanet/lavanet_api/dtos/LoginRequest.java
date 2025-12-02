package com.lavanet.lavanet_api.dtos;

import lombok.Data;

@Data
public class LoginRequest {
    private String correo;
    private String password;
}