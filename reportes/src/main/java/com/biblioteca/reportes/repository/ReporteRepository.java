package com.biblioteca.reportes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.reportes.model.Reporte;


public interface ReporteRepository extends JpaRepository<Reporte, Long> {
    
    // Devuelve el historial de reportes de un usuario, ideal para mostrar en su panel
    List<Reporte> findByUsuarioIdOrderByFechaSolicitudDesc(String usuarioId);
}