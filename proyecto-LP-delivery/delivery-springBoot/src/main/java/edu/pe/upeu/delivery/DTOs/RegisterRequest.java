package edu.pe.upeu.delivery.DTOs;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String rol;
    private String empresa;

    // Datos del cliente
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String email;
    private String foto;

    // Dirección completa
    private String calle;
    private String numero;
    private String referencia;
    private String distrito;
    private String ciudad;
}
