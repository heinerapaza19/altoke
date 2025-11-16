package edu.pe.upeu.delivery.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPago;

    @Column(nullable = false)
    private BigDecimal monto;

    @Column(nullable = false)
    private String metodo;

    @Column(nullable = false)
    private String estado;

    // ✅ solo una variable, con valor inicial automático
    @Column(nullable = false)
    private LocalDateTime fechaPago = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "pedido_id", referencedColumnName = "idPedido")
    private Pedido pedido;
}
