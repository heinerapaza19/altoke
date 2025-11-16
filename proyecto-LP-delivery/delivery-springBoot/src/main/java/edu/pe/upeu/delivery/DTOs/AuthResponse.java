package edu.pe.upeu.delivery.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String type = "Bearer";
    private Long idUsuario;
    private String username;
    private String rol;
    private String empresa;
    private boolean activo;
    private String mensaje;
}

