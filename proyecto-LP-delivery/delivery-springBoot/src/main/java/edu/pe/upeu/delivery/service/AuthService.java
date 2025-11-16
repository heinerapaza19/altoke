package edu.pe.upeu.delivery.service;

import edu.pe.upeu.delivery.DTOs.AuthResponse;
import edu.pe.upeu.delivery.DTOs.LoginRequest;
import edu.pe.upeu.delivery.DTOs.RegisterRequest;
import edu.pe.upeu.delivery.model.Cliente;
import edu.pe.upeu.delivery.model.Direccion;
import edu.pe.upeu.delivery.model.Empresa;
import edu.pe.upeu.delivery.model.Rol;
import edu.pe.upeu.delivery.model.Usuario;
import edu.pe.upeu.delivery.repository.ClienteRepository;
import edu.pe.upeu.delivery.repository.DireccionRepository;
import edu.pe.upeu.delivery.repository.EmpresaRepository;
import edu.pe.upeu.delivery.repository.RolRepository;
import edu.pe.upeu.delivery.repository.UsuarioRepository;
import edu.pe.upeu.delivery.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final EmpresaRepository empresaRepository;
    private final ClienteRepository clienteRepository;
    private final DireccionRepository direccionRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // ===================== LOGIN =====================
    public AuthResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!usuario.isActivo()) {
            throw new RuntimeException("Usuario inactivo");
        }

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        String rol = usuario.getRol() != null ? usuario.getRol().getNombre() : "SIN_ROL";
        String empresa = usuario.getEmpresa() != null ? usuario.getEmpresa().getNombre() : "SIN_EMPRESA";

        String token = jwtUtil.generateToken(
                usuario.getUsername(),
                usuario.getIdUsuario(),
                rol,
                empresa
        );

        return AuthResponse.builder()
                .token(token)
                .type("Bearer")
                .idUsuario(usuario.getIdUsuario())
                .username(usuario.getUsername())
                .rol(rol)
                .empresa(empresa)
                .activo(usuario.isActivo())
                .mensaje("Login exitoso")
                .build();
    }

    // ===================== REGISTRO GENERAL CLIENTE =====================
    @Transactional
    public AuthResponse registrar(RegisterRequest request) {

        // 1️⃣ Validar username
        if (usuarioRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("El usuario ya existe");
        }

        // 2️⃣ Crear Dirección
        Direccion direccion = new Direccion();
        direccion.setCalle(request.getCalle());
        direccion.setNumero(request.getNumero());
        direccion.setReferencia(request.getReferencia());
        direccion.setDistrito(request.getDistrito());
        direccion.setCiudad(request.getCiudad());

        Direccion direccionGuardada = direccionRepository.save(direccion);

        // 3️⃣ Crear Cliente
        Cliente cliente = new Cliente();
        cliente.setNombre(request.getNombre());
        cliente.setApellido(request.getApellido());
        cliente.setDni(request.getDni());
        cliente.setTelefono(request.getTelefono());
        cliente.setEmail(request.getEmail());
        cliente.setFoto(request.getFoto());
        cliente.setDireccion(direccionGuardada);

        Cliente clienteGuardado = clienteRepository.save(cliente);

        // 4️⃣ Buscar rol
        Rol rol = rolRepository.findByNombre(request.getRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + request.getRol()));

        // 5️⃣ Buscar empresa
        Empresa empresa = empresaRepository.findByNombre(request.getEmpresa())
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada: " + request.getEmpresa()));

        // 6️⃣ Crear usuario
        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setActivo(true);
        usuario.setRol(rol);
        usuario.setEmpresa(empresa);
        usuario.setCliente(clienteGuardado);

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // 7️⃣ Generar token
        String token = jwtUtil.generateToken(
                usuarioGuardado.getUsername(),
                usuarioGuardado.getIdUsuario(),
                rol.getNombre(),
                empresa.getNombre()
        );

        // 8️⃣ Respuesta final
        return AuthResponse.builder()
                .token(token)
                .type("Bearer")
                .idUsuario(usuarioGuardado.getIdUsuario())
                .username(usuarioGuardado.getUsername())
                .rol(rol.getNombre())
                .empresa(empresa.getNombre())
                .activo(usuarioGuardado.isActivo())
                .mensaje("Usuario registrado correctamente")
                .build();
    }

    // ===================== LOGOUT =====================
    public AuthResponse logout(String token) {
        return AuthResponse.builder()
                .mensaje("Logout exitoso")
                .build();
    }
}
