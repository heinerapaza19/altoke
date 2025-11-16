package edu.pe.upeu.delivery.repository;

import edu.pe.upeu.delivery.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    @Query("SELECT u FROM Usuario u LEFT JOIN FETCH u.cliente WHERE u.username = :username")
    Optional<Usuario> findByUsernameWithCliente(@Param("username") String username);

    @Query("SELECT u FROM Usuario u WHERE u.empresa.idEmpresa = :empresaId")
    List<Usuario> findByEmpresaId(@Param("empresaId") Long empresaId);
}
