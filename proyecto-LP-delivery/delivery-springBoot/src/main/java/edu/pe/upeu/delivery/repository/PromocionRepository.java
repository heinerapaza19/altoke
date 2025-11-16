package edu.pe.upeu.delivery.repository;

import edu.pe.upeu.delivery.model.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromocionRepository extends JpaRepository<Promocion, Long> {

    // Buscar solo promociones activas
    List<Promocion> findByActivo(boolean activo);

}
