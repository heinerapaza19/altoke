package edu.pe.upeu.delivery.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Repartidor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRepartidor;

    private String nombre;
    private String telefono;
    private String placaMoto;
    private String licencia;

    // ✅ Campo que faltaba
    private boolean disponible;

    // 🔹 Relación con empresa
    @ManyToOne
    @JoinColumn(name = "empresa_id", referencedColumnName = "idEmpresa")
    private Empresa empresa;
}
