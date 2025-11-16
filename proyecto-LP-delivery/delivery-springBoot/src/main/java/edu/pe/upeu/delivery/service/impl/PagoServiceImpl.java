package edu.pe.upeu.delivery.service.impl;


import edu.pe.upeu.delivery.DTOs.PagoDTO;
import edu.pe.upeu.delivery.mapper.PagoMapper;
import edu.pe.upeu.delivery.model.Pago;
import edu.pe.upeu.delivery.model.Pedido;
import edu.pe.upeu.delivery.repository.PagoRepository;
import edu.pe.upeu.delivery.repository.PedidoRepository;
import edu.pe.upeu.delivery.service.PagoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagoServiceImpl implements PagoService {

    private final PagoRepository repo;
    private final PedidoRepository pedidoRepo;
    private final PagoMapper mapper;

    public PagoServiceImpl(PagoRepository repo, PedidoRepository pedidoRepo, PagoMapper mapper) {
        this.repo = repo;
        this.pedidoRepo = pedidoRepo;
        this.mapper = mapper;
    }

    @Override
    public List<PagoDTO> listarPagos() {
        return repo.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    public PagoDTO obtenerPorId(Long id) {
        return repo.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
    }

    @Override
    public PagoDTO registrarPago(PagoDTO dto) {
        Pedido pedido = pedidoRepo.findById(dto.getIdPedido())
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        Pago entity = mapper.toEntity(dto, pedido);
        return mapper.toDTO(repo.save(entity));
    }

    @Override
    public PagoDTO actualizarPago(Long id, PagoDTO dto) {
        Pago pago = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
        pago.setMonto(dto.getMonto());
        pago.setMetodo(dto.getMetodo());
        pago.setEstado(dto.getEstado());
        return mapper.toDTO(repo.save(pago));
    }

    @Override
    public void eliminarPago(Long id) {
        repo.deleteById(id);
    }
}
