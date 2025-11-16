package edu.pe.upeu.delivery.repository;

import edu.pe.upeu.delivery.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByEmail(String email);
    
    // Buscar por email ignorando mayúsculas/minúsculas para evitar duplicados
    Optional<Cliente> findByEmailIgnoreCase(String email);
}
