package edu.pe.upeu.delivery.model;



import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmpresa;

    private String nombre;
    private String ruc;
    private String direccion;
    private String telefono;
    private String correo;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private List<Producto> productos;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
    private List<Repartidor> repartidores;
}
