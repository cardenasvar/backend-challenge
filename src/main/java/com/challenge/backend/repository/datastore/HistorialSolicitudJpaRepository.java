package com.challenge.backend.repository.datastore;

import com.challenge.backend.repository.datastore.model.HistorialSolicitudModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * HistorialSolicitudRepositoryImpl
 *
 * @author Jonathan Cárdenas
 * @since 2025-09-14
 */
@Repository
public interface HistorialSolicitudJpaRepository extends JpaRepository<HistorialSolicitudModel, Long> {

}
