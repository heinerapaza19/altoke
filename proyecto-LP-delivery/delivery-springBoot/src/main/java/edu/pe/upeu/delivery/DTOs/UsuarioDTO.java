package edu.pe.upeu.delivery.DTOs;

import lombok.Data;

@Data
public class UsuarioDTO {
    private Long idUsuario;
    private String username;
    private boolean activo;
    private String rol;     // solo el nombre del rol
    private String empresa; // solo el nombre de la empresa
}
