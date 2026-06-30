package com.biblioteca.reportes.controller;

import com.biblioteca.reportes.dto.ReporteRequestDto;
import com.biblioteca.reportes.model.Reporte;
import com.biblioteca.reportes.repository.ReporteRepository;
import com.biblioteca.reportes.service.ReporteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService service;
    private final ReporteRepository repository; // Inyectado directamente solo para consultas rápidas de estado

    /**
     * 1. Endpoint para solicitar la creación de un nuevo reporte (Asíncrono).
     */
    @PostMapping
    public ResponseEntity<Reporte> crearReporte(@Valid @RequestBody ReporteRequestDto requestDto) {
        log.info("HTTP POST: Solicitando reporte '{}' para usuario '{}'", requestDto.nombre(), requestDto.usuarioId());

        // Mapeo manual de DTO a Entidad
        Reporte nuevoReporte = new Reporte();
        nuevoReporte.setNombre(requestDto.nombre());
        nuevoReporte.setTipo(requestDto.tipo());
        nuevoReporte.setUsuarioId(requestDto.usuarioId());
        nuevoReporte.setFiltros(requestDto.filtros());

        // Guardamos inicialmente en la BD (estado PENDIENTE)
        Reporte guardado = service.solicitarReporte(nuevoReporte);

        // Disparamos el proceso pesado en segundo plano gracias al @Async del servicio
        service.procesarYGenerarReporte(guardado.getId());

        // Respondemos de inmediato con un 202 Accepted y los datos del reporte en curso
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(guardado);
    }

    /**
     * 2. Endpoint para consultar el estado actual de un reporte específico (Polling).
     */
    @GetMapping("/{id}")
    public ResponseEntity<Reporte> obtenerEstadoReporte(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * 3. Endpoint para ver el historial de reportes solicitados por un usuario.
     */
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Reporte>> listarReportesPorUsuario(@PathVariable String usuarioId) {
        List<Reporte> historial = repository.findByUsuarioIdOrderByFechaSolicitudDesc(usuarioId);
        return ResponseEntity.ok(historial);
    }
}