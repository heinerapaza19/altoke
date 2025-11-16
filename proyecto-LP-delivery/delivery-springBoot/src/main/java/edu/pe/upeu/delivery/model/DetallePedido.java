package edu.pe.upeu.delivery.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Data
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalle;

    private int cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;

    // ✅ Relación con Pedido
    @ManyToOne
    @JoinColumn(name = "pedido_id", referencedColumnName = "idPedido")
    private Pedido pedido;

    // ✅ Relación con Producto
    @ManyToOne
    @JoinColumn(name = "producto_id", referencedColumnName = "idProducto")
    private Producto producto;
}
