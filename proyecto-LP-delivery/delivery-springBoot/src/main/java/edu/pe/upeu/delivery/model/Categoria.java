package edu.pe.upeu.delivery.model;



import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategoria;

    private String nombre;
    private String descripcion;
    private String imagen;


    @OneToMany(mappedBy = "categoria")
    private List<Producto> productos;
}
