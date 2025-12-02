package com.lavanet.lavanet_api.dtos;

import lombok.Data;

@Data
public class RegisterRequest {
    private String nombres;
    private String apellidos;
    private String fechaNacimiento;
    private String direccion;
    private String correo;
    private String password;
    private String rol;
    private String telefono;
}