package com.biblioteca.Prestamo.repository;

import com.biblioteca.Prestamo.model.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    // Spring crea automáticamente esta consulta por el nombre del método
    List<Prestamo> findByUsuarioId(Long usuarioId);
}