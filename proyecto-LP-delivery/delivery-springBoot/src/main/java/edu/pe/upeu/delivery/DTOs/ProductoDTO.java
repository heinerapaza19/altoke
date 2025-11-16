package edu.pe.upeu.delivery.DTOs;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductoDTO {
    private Long idProducto;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private String imagen;
    private boolean disponible;
    private String categoria; // nombre de la categoría
    private String empresa;   // nombre de la empresa o tienda
}
