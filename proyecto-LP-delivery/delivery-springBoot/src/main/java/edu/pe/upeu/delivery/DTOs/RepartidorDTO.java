package edu.pe.upeu.delivery.DTOs;

import lombok.Data;

@Data
public class RepartidorDTO {
    private Long idRepartidor;
    private String nombre;
    private String telefono;
    private String placaMoto;
    private String licencia;
    private boolean disponible;
    private String empresa;
}
