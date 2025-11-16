package edu.pe.upeu.delivery.mapper;

import edu.pe.upeu.delivery.DTOs.PagoDTO;
import edu.pe.upeu.delivery.model.Pago;
import edu.pe.upeu.delivery.model.Pedido;
import org.springframework.stereotype.Component;

@Component
public class PagoMapper {

    // 🔹 Convierte de entidad a DTO
    public PagoDTO toDTO(Pago entity) {
        if (entity == null) return null;

        PagoDTO dto = new PagoDTO();
        dto.setIdPago(entity.getIdPago());
        dto.setMonto(entity.getMonto());
        dto.setMetodo(entity.getMetodo());
        dto.setEstado(entity.getEstado());
        dto.setFechaPago(entity.getFechaPago());

        // ✅ Relación con pedido
        if (entity.getPedido() != null) {
            dto.setIdPedido(entity.getPedido().getIdPedido());
        }

        return dto;
    }

    // 🔹 Convierte de DTO a entidad (recibe también el pedido relacionado)
    public Pago toEntity(PagoDTO dto, Pedido pedido) {
        if (dto == null) return null;

        Pago entity = new Pago();
        entity.setIdPago(dto.getIdPago());
        entity.setMonto(dto.getMonto());
        entity.setMetodo(dto.getMetodo());
        entity.setEstado(dto.getEstado());
        entity.setFechaPago(dto.getFechaPago());

        if (pedido != null) {
            entity.setPedido(pedido);
        }

        return entity;
    }
}
