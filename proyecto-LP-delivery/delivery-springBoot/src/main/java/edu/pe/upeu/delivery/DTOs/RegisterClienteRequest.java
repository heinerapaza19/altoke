package edu.pe.upeu.delivery.DTOs;

import lombok.Data;

@Data
public class RegisterClienteRequest {
    // Datos del usuario
    private String username;
    private String password;
    
    // Datos del cliente
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String email;
    
    // Datos de dirección
    private String calle;
    private String numero;
    private String referencia;
    private String distrito;
    private String ciudad;
    
    // Empresa (opcional, por defecto será la primera)
    private String empresa;
}

