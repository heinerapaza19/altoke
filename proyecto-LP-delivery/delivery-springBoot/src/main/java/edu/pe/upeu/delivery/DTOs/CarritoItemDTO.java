package edu.pe.upeu.delivery.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CarritoItemDTO {
    private Long idProducto;
    private String nombre;
    private String imagen;
    private int cantidad;
    private BigDecimal precio;
    private BigDecimal subtotal;
}
