package edu.pe.upeu.delivery.controller;


import edu.pe.upeu.delivery.DTOs.UsuarioDTO;
import edu.pe.upeu.delivery.service.UsuarioService;
import edu.pe.upeu.delivery.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;
    private final JwtUtil jwtUtil;

    public UsuarioController(UsuarioService service, JwtUtil jwtUtil) {
        this.service = service;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public List<UsuarioDTO> listar() {
        return service.listarUsuarios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @GetMapping("/perfil")
    public ResponseEntity<UsuarioDTO> obtenerPerfil(HttpServletRequest request) {
        try {
            // Obtener el token del header
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(org.springframework.http.HttpStatus.UNAUTHORIZED).build();
            }

            String token = authHeader.substring(7);
            
            // Extraer el ID del usuario del token
            Long idUsuario = jwtUtil.extractClaim(token, claims -> claims.get("idUsuario", Long.class));
            
            if (idUsuario == null) {
                return ResponseEntity.status(org.springframework.http.HttpStatus.UNAUTHORIZED).build();
            }

            UsuarioDTO usuario = service.obtenerPorId(idUsuario);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.status(org.springframework.http.HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioDTO> obtenerMiPerfil() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated() || 
                authentication.getName().equals("anonymousUser")) {
                return ResponseEntity.status(org.springframework.http.HttpStatus.UNAUTHORIZED).build();
            }

            String username = authentication.getName();
            UsuarioDTO usuario = service.buscarPorUsername(username);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.status(org.springframework.http.HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping
    public UsuarioDTO guardar(@RequestBody UsuarioDTO dto) {
        return service.guardarUsuario(dto);
    }

    @PutMapping("/{id}")
    public UsuarioDTO actualizar(@PathVariable Long id, @RequestBody UsuarioDTO dto) {
        return service.actualizarUsuario(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminarUsuario(id);
    }

    @GetMapping("/buscar/{username}")
    public UsuarioDTO buscarPorUsername(@PathVariable String username) {
        return service.buscarPorUsername(username);
    }
}
