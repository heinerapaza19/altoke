package edu.pe.upeu.delivery.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.*;

import edu.pe.upeu.delivery.repository.*;

@RestController
@RequestMapping("/api/admin/dashboard")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class DashboardController {

    private final ProductoRepository productoRepo;
    private final PedidoRepository pedidoRepo;
    private final RepartidorRepository repartidorRepo;
    private final PromocionRepository promocionRepo;

    @GetMapping
    public Map<String, Object> obtenerDashboard() {

        Map<String, Object> data = new HashMap<>();

        /** ====== TARJETAS CON BD REAL ====== */
        data.put("productos", productoRepo.count());
        data.put("pedidos", pedidoRepo.count());
        data.put("repartidores", repartidorRepo.count());
        data.put("promociones", promocionRepo.count());

        /** ====== ESTADO DE PEDIDOS ====== */
        data.put("pedidosCompletados", pedidoRepo.countByEstado("COMPLETADO"));
        data.put("pedidosPendientes", pedidoRepo.countByEstado("PENDIENTE"));
        data.put("pedidosCancelados", pedidoRepo.countByEstado("CANCELADO"));

        return data;
    }
}
