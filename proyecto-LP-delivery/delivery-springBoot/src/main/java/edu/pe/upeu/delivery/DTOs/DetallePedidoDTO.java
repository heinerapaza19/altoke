package edu.pe.upeu.delivery.DTOs;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class DetallePedidoDTO {
    private Long idDetalle;     // ID del detalle del pedido
    private String producto;    // Nombre del producto
    private int cantidad;       // Cantidad comprada
    private BigDecimal subtotal; // Subtotal = cantidad × precio unitario
}
