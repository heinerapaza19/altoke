package edu.pe.upeu.delivery.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    private String username;
    private String password;
    private boolean activo;

    // 🔹 Un usuario tiene un rol (Administrador, Repartidor, etc.)
    @ManyToOne
    @JoinColumn(name = "rol_id", referencedColumnName = "idRol")
    private Rol rol;

    // 🔹 Un usuario pertenece a una empresa
    @ManyToOne
    @JoinColumn(name = "empresa_id", referencedColumnName = "idEmpresa")
    private Empresa empresa;

    // 🔹 Un usuario puede tener un cliente asociado (relación opcional)
    @OneToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "idCliente")
    private Cliente cliente;
}
