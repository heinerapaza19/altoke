package edu.pe.upeu.delivery.config;



import edu.pe.upeu.delivery.model.Rol;
import edu.pe.upeu.delivery.model.Empresa;
import edu.pe.upeu.delivery.repository.RolRepository;
import edu.pe.upeu.delivery.repository.EmpresaRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final RolRepository rolRepository;
    private final EmpresaRepository empresaRepository;

    @PostConstruct
    public void init() {

        crearRol("ADMIN", "Administrador del sistema");
        crearRol("EMPRESA", "Dueño o encargado de empresa");
        crearRol("REPARTIDOR", "Repartidor de pedidos");
        crearRol("CLIENTE", "Cliente que realiza pedidos");

        crearEmpresa("AlToque");
        crearEmpresa("MiEmpresa"); // Puedes agregar más
    }

    private void crearRol(String nombre, String descripcion) {
        rolRepository.findByNombre(nombre).orElseGet(() -> {
            Rol rol = new Rol();
            rol.setNombre(nombre);
            rol.setDescripcion(descripcion);
            rolRepository.save(rol);
            System.out.println("✔ Rol creado: " + nombre);
            return rol;
        });
    }

    private void crearEmpresa(String nombre) {
        empresaRepository.findByNombre(nombre).orElseGet(() -> {
            Empresa empresa = new Empresa();
            empresa.setNombre(nombre);
            empresaRepository.save(empresa);
            System.out.println("✔ Empresa creada: " + nombre);
            return empresa;
        });
    }
}
