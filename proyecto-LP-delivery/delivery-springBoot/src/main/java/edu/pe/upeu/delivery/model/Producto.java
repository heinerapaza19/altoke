package edu.pe.upeu.delivery.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;

    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private String imagen;


    // ✅ Agrega este campo si falta
    private boolean disponible;

    @ManyToOne
    @JoinColumn(name = "categoria_id", referencedColumnName = "idCategoria")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "empresa_id", referencedColumnName = "idEmpresa")
    private Empresa empresa;
}
