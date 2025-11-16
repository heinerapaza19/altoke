package edu.pe.upeu.delivery.DTOs;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PedidoDTO {
    private Long idPedido;
    private LocalDateTime fecha;
    private String estado;
    private BigDecimal total;
    private String cliente;
    private String repartidor;
    private List<DetallePedidoDTO> detalles;
}
