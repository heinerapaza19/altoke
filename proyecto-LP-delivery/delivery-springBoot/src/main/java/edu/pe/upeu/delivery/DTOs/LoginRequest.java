package edu.pe.upeu.delivery.DTOs;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}

