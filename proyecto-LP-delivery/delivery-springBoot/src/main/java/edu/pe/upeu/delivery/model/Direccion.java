package edu.pe.upeu.delivery.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDireccion;

    private String calle;
    private String numero;
    private String referencia;
    private String distrito;
    private String ciudad;

    @OneToOne(mappedBy = "direccion")
    private Cliente cliente;
}
