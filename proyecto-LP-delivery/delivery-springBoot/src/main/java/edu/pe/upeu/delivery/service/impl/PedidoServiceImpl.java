package edu.pe.upeu.delivery.service.impl;


import edu.pe.upeu.delivery.DTOs.PedidoDTO;
import edu.pe.upeu.delivery.mapper.PedidoMapper;
import edu.pe.upeu.delivery.model.*;
import edu.pe.upeu.delivery.repository.*;
import edu.pe.upeu.delivery.service.CarritoService;
import edu.pe.upeu.delivery.service.PedidoService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepo;
    private final ClienteRepository clienteRepo;
    private final ProductoRepository productoRepo;
    private final DetallePedidoRepository detalleRepo;
    private final CarritoService carritoService;
    private final PedidoMapper mapper;
    private final UsuarioRepository usuarioRepo;

    public PedidoServiceImpl(PedidoRepository pedidoRepo, ClienteRepository clienteRepo,
                             ProductoRepository productoRepo, DetallePedidoRepository detalleRepo,
                             CarritoService carritoService, PedidoMapper mapper,
                             UsuarioRepository usuarioRepo) {
        this.pedidoRepo = pedidoRepo;
        this.clienteRepo = clienteRepo;
        this.productoRepo = productoRepo;
        this.detalleRepo = detalleRepo;
        this.carritoService = carritoService;
        this.mapper = mapper;
        this.usuarioRepo = usuarioRepo;
    }

    @Override
    public List<PedidoDTO> listarPedidos() {
        return pedidoRepo.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    public List<PedidoDTO> listarPedidosPorUsuario(String username) {
        // Buscar el usuario
        Usuario usuario = usuarioRepo.findByUsernameWithCliente(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));

        // Obtener el cliente asociado
        Cliente cliente = usuario.getCliente();
        
        // Si no tiene cliente asociado, buscar por email
        if (cliente == null) {
            cliente = clienteRepo.findByEmail(usuario.getUsername()).orElse(null);
        }
        
        // Si aún no tiene cliente, retornar lista vacía (no puede tener pedidos sin cliente)
        if (cliente == null) {
            return List.of();
        }

        // Buscar todos los pedidos del cliente
        return pedidoRepo.findByCliente(cliente).stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public PedidoDTO obtenerPorId(Long id) {
        return pedidoRepo.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado: " + id));
    }

    @Override
    public PedidoDTO crearPedidoDesdeCarrito(Long idCliente) {
        // Buscar el cliente, si no existe intentar crearlo desde el usuario autenticado
        Cliente cliente = clienteRepo.findById(idCliente).orElse(null);
        
        // Si el cliente no existe, intentar obtenerlo del usuario autenticado
        if (cliente == null) {
            try {
                // Intentar obtener el username del contexto de seguridad
                org.springframework.security.core.Authentication authentication = 
                    org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
                
                if (authentication != null && authentication.isAuthenticated() && 
                    !authentication.getName().equals("anonymousUser")) {
                    String username = authentication.getName();
                    // Intentar crear el pedido usando el usuario autenticado
                    return crearPedidoDesdeCarritoUsuarioAutenticado(username);
                }
            } catch (Exception e) {
                // Si falla, continuar con el error original
            }
            
            // Si no hay usuario autenticado o falla, lanzar error
            throw new RuntimeException("Cliente no encontrado con ID: " + idCliente + 
                ". Si eres un usuario autenticado, usa el endpoint /pedidos/crear (sin ID) para crear el pedido automáticamente.");
        }

        // Validar que el carrito no esté vacío
        List<edu.pe.upeu.delivery.dto.CarritoItemDTO> carrito = carritoService.obtenerCarrito();
        if (carrito == null || carrito.isEmpty()) {
            throw new RuntimeException("El carrito está vacío. Agrega productos antes de crear el pedido.");
        }

        // Calcular el total
        java.math.BigDecimal total = carritoService.calcularTotal();
        if (total == null || total.compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("El total del carrito debe ser mayor a cero.");
        }

        // Crear el pedido
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setFecha(LocalDateTime.now());
        pedido.setEstado("PENDIENTE");
        pedido.setTotal(total);

        Pedido saved = pedidoRepo.save(pedido);

        // Guardar detalles desde el carrito
        for (edu.pe.upeu.delivery.dto.CarritoItemDTO item : carrito) {
            if (item.getIdProducto() == null) {
                throw new RuntimeException("El item del carrito no tiene ID de producto válido");
            }

            Producto producto = productoRepo.findById(item.getIdProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + item.getIdProducto()));

            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(saved);
            detalle.setProducto(producto);
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(item.getPrecio()); // ✅ Agregar precio unitario
            detalle.setSubtotal(item.getSubtotal());

            detalleRepo.save(detalle);
        }

        // Limpiar el carrito después de crear el pedido
        carritoService.vaciarCarrito();
        
        // Recargar el pedido con los detalles para retornarlo completo
        Pedido pedidoCompleto = pedidoRepo.findById(saved.getIdPedido())
                .orElseThrow(() -> new RuntimeException("Error al recuperar el pedido creado"));
        
        return mapper.toDTO(pedidoCompleto);
    }

    @Override
    public PedidoDTO crearPedidoDesdeCarritoUsuarioAutenticado(String username) {
        // Buscar el usuario por username con cliente cargado (JOIN FETCH)
        Usuario usuario = usuarioRepo.findByUsernameWithCliente(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));

        // Obtener el cliente asociado al usuario
        Cliente cliente = usuario.getCliente();
        
        // Si no tiene cliente asociado directamente, intentar buscar por email (case-insensitive)
        if (cliente == null) {
            cliente = clienteRepo.findByEmailIgnoreCase(usuario.getUsername())
                    .orElse(null);
        }
        
        // Si aún no se encuentra, buscar por email similar
        if (cliente == null) {
            // Buscar cualquier cliente que tenga el email similar al username
            List<Cliente> clientes = clienteRepo.findAll();
            cliente = clientes.stream()
                    .filter(c -> c.getEmail() != null && 
                                (c.getEmail().equalsIgnoreCase(username) || 
                                 c.getEmail().toLowerCase().contains(username.toLowerCase()) ||
                                 username.toLowerCase().contains(c.getEmail().split("@")[0].toLowerCase())))
                    .findFirst()
                    .orElse(null);
        }
        
        // Si aún no se encuentra, verificar si existe un cliente con el mismo email antes de crear uno nuevo
        if (cliente == null) {
            // Generar email si el username no es un email
            String email = usuario.getUsername().contains("@") 
                ? usuario.getUsername() 
                : usuario.getUsername() + "@email.com";
            
            // Buscar si ya existe un cliente con ese email (case-insensitive)
            cliente = clienteRepo.findByEmailIgnoreCase(email).orElse(null);
            
            // Si no existe, crear uno nuevo
            if (cliente == null) {
                cliente = new Cliente();
                
                // Usar el username como nombre si no hay otro disponible
                cliente.setNombre(usuario.getUsername());
                cliente.setApellido(""); // Se puede actualizar después
                cliente.setEmail(email);
                
                // Generar foto aleatoria
                cliente.setFoto("https://i.pravatar.cc/300?img=" + (int)(Math.random() * 70));
                
                // Guardar el cliente
                cliente = clienteRepo.save(cliente);
                
                // Log para debugging
                System.out.println("✅ Cliente creado automáticamente para usuario: " + username + 
                                 " (ID Cliente: " + cliente.getIdCliente() + ")");
            } else {
                // Si el cliente ya existe, solo asociarlo al usuario
                System.out.println("✅ Cliente existente encontrado por email para usuario: " + username + 
                                 " (ID Cliente: " + cliente.getIdCliente() + ")");
            }
            
            // Asociar el cliente al usuario para futuras consultas (ya sea nuevo o existente)
            usuario.setCliente(cliente);
            usuarioRepo.save(usuario);
        }

        // Usar el método existente con el ID del cliente
        return crearPedidoDesdeCarrito(cliente.getIdCliente());
    }

    @Override
    public PedidoDTO actualizarEstado(Long idPedido, String nuevoEstado) {
        Pedido pedido = pedidoRepo.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado: " + idPedido));
        pedido.setEstado(nuevoEstado);
        return mapper.toDTO(pedidoRepo.save(pedido));
    }

    @Override
    public void eliminarPedido(Long idPedido) {
        pedidoRepo.deleteById(idPedido);
    }
}
