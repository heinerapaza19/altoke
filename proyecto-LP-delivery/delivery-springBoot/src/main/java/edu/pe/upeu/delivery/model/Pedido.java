package edu.pe.upeu.delivery.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;

    // ✅ fecha se inicializa automáticamente
    @Column(nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();

    // ✅ estado inicial por defecto
    @Column(nullable = false)
    private String estado = "PENDIENTE";

    @Column(nullable = false)
    private BigDecimal total;

    // 🔹 Relación con Cliente
    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "idCliente")
    private Cliente cliente;

    // 🔹 Relación con Repartidor
    @ManyToOne
    @JoinColumn(name = "repartidor_id", referencedColumnName = "idRepartidor")
    private Repartidor repartidor;

    // 🔹 Relación con DetallePedido (Lista de productos)
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<DetallePedido> detalles = new ArrayList<>();
}
