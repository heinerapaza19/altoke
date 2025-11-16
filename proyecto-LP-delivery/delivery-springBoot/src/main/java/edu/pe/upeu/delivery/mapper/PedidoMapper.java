package edu.pe.upeu.delivery.mapper;

import edu.pe.upeu.delivery.DTOs.DetallePedidoDTO;
import edu.pe.upeu.delivery.DTOs.PedidoDTO;
import edu.pe.upeu.delivery.model.DetallePedido;
import edu.pe.upeu.delivery.model.Pedido;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PedidoMapper {

    // 🔹 Convierte de entidad a DTO
    public PedidoDTO toDTO(Pedido entity) {
        if (entity == null) return null;

        PedidoDTO dto = new PedidoDTO();
        dto.setIdPedido(entity.getIdPedido());
        dto.setFecha(entity.getFecha());
        dto.setEstado(entity.getEstado());
        dto.setTotal(entity.getTotal());

        // 🔹 Evitar NullPointerException
        dto.setCliente(
                entity.getCliente() != null ? entity.getCliente().getNombre() : null
        );

        dto.setRepartidor(
                entity.getRepartidor() != null ? entity.getRepartidor().getNombre() : null
        );

        // 🔹 Mapea los detalles si existen
        if (entity.getDetalles() != null && !entity.getDetalles().isEmpty()) {
            dto.setDetalles(
                    entity.getDetalles()
                            .stream()
                            .map(this::toDetalleDTO)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    // 🔹 Convierte cada DetallePedido en DetallePedidoDTO
    private DetallePedidoDTO toDetalleDTO(DetallePedido det) {
        if (det == null) return null;

        DetallePedidoDTO d = new DetallePedidoDTO();
        d.setIdDetalle(det.getIdDetalle());
        d.setProducto(det.getProducto() != null ? det.getProducto().getNombre() : null);
        d.setCantidad(det.getCantidad());
        d.setSubtotal(det.getSubtotal());

        return d;
    }
}
