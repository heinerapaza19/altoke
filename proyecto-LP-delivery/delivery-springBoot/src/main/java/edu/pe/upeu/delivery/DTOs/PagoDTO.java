package edu.pe.upeu.delivery.DTOs;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PagoDTO {
    private Long idPago;
    private BigDecimal monto;
    private String metodo;
    private String estado;
    private LocalDateTime fechaPago;
    private Long idPedido; // 🔹 Relación al pedido
}
