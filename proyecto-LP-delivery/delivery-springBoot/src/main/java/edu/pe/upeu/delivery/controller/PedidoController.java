package edu.pe.upeu.delivery.controller;


import edu.pe.upeu.delivery.DTOs.PedidoDTO;
import edu.pe.upeu.delivery.service.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @GetMapping
    public List<PedidoDTO> listar() {
        // Obtener el usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // Si no hay autenticación o es anónimo, retornar todos los pedidos (público)
        if (authentication == null || !authentication.isAuthenticated() || 
            authentication.getName().equals("anonymousUser")) {
            return service.listarPedidos();
        }
        
        // Obtener el rol del usuario desde las authorities
        String rol = authentication.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .filter(a -> a.startsWith("ROLE_"))
                .map(a -> a.substring(5)) // Remover "ROLE_"
                .findFirst()
                .orElse("");
        
        // Si es ADMINISTRADOR, retornar todos los pedidos
        if ("ADMINISTRADOR".equalsIgnoreCase(rol)) {
            return service.listarPedidos();
        }
        
        // Si es CLIENTE, retornar solo sus pedidos
        if ("CLIENTE".equalsIgnoreCase(rol)) {
            String username = authentication.getName();
            return service.listarPedidosPorUsuario(username);
        }
        
        // Para otros roles (REPARTIDOR, etc.), retornar todos por defecto
        // Puedes personalizar esto según tus necesidades
        return service.listarPedidos();
    }

    @GetMapping("/{id}")
    public PedidoDTO obtener(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearPedidoDesdeUsuarioAutenticado() {
        try {
            // Obtener el username del usuario autenticado desde el token JWT
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return ResponseEntity.status(org.springframework.http.HttpStatus.UNAUTHORIZED)
                        .body(new ErrorResponse("Usuario no autenticado. Por favor, inicia sesión."));
            }

            String username = authentication.getName();
            PedidoDTO pedido = service.crearPedidoDesdeCarritoUsuarioAutenticado(username);
            return ResponseEntity.status(org.springframework.http.HttpStatus.CREATED).body(pedido);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    @PostMapping("/crear/{idCliente}")
    public ResponseEntity<?> crearPedido(@PathVariable Long idCliente) {
        try {
            // Intentar obtener el usuario autenticado si existe
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated() && 
                !authentication.getName().equals("anonymousUser")) {
                // Si hay un usuario autenticado, intentar usar su cliente asociado
                String username = authentication.getName();
                try {
                    PedidoDTO pedido = service.crearPedidoDesdeCarritoUsuarioAutenticado(username);
                    return ResponseEntity.status(org.springframework.http.HttpStatus.CREATED).body(pedido);
                } catch (RuntimeException e) {
                    // Si falla, continuar con el método original usando el ID
                }
            }
            
            // Método original: usar el ID del cliente proporcionado
            PedidoDTO pedido = service.crearPedidoDesdeCarrito(idCliente);
            return ResponseEntity.status(org.springframework.http.HttpStatus.CREATED).body(pedido);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    // Clase interna para respuestas de error
    private static class ErrorResponse {
        private String mensaje;
        
        public ErrorResponse(String mensaje) {
            this.mensaje = mensaje;
        }
        
        public String getMensaje() {
            return mensaje;
        }
    }

    @PutMapping("/{id}/estado/{nuevoEstado}")
    public PedidoDTO actualizarEstado(@PathVariable Long id, @PathVariable String nuevoEstado) {
        return service.actualizarEstado(id, nuevoEstado);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminarPedido(id);
    }
}
