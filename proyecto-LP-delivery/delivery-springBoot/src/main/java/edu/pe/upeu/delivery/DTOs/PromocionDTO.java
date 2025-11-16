package edu.pe.upeu.delivery.DTOs;



import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PromocionDTO {
    private Long idPromocion;
    private String nombre;
    private String descripcion;
    private BigDecimal descuento;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Long idProducto; // solo el id para simplificar
}
