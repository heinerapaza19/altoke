package edu.pe.upeu.delivery.repository;

import edu.pe.upeu.delivery.model.Cliente;
import edu.pe.upeu.delivery.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    /** 🔹 Listar pedidos por cliente */
    List<Pedido> findByCliente(Cliente cliente);

    /** 🔹 Contar pedidos por estado (COMPLETADO, PENDIENTE, CANCELADO) */
    long countByEstado(String estado);
}
