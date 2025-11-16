package edu.pe.upeu.delivery.repository;

import edu.pe.upeu.delivery.model.Repartidor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepartidorRepository extends JpaRepository<Repartidor, Long> {

}
