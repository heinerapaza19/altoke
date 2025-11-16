package edu.pe.upeu.delivery.DTOs;


import lombok.Data;

@Data
public class ClienteDTO {
    private Long idCliente;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private String foto;

    // ✔ Dirección visible como texto simple
    private String calle;
    private String numero;
    private String referencia;
    private String distrito;
    private String ciudad;
}

