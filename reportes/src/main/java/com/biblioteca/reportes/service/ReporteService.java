package com.biblioteca.reportes.service;

import com.biblioteca.reportes.model.EstadoReporte;
import com.biblioteca.reportes.model.Reporte;
import com.biblioteca.reportes.repository.ReporteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReporteService {

    private final ReporteRepository repository;

    /**
     * Paso 1: Registra la intención de generar un reporte.
     */
    @Transactional
    public Reporte solicitarReporte(Reporte reporte) {
        log.info("Solicitud de reporte recibida: '{}' para el usuario: {}", 
                reporte.getNombre(), reporte.getUsuarioId());
        
        // El @PrePersist de la entidad lo guarda automáticamente como PENDIENTE
        return repository.save(reporte);
    }

    /**
     * Paso 2: Generación pesada del reporte en segundo plano.
     * La anotación @Async le dice a Spring que ejecute esto en un hilo separado del pool.
     */
    @Async
    @Transactional
    public void procesarYGenerarReporte(Long reporteId) {
        Reporte reporte = repository.findById(reporteId)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el reporte con ID: " + reporteId));

        try {
            // 1. Cambiamos el estado a PROCESANDO
            reporte.setEstado(EstadoReporte.PROCESANDO);
            repository.saveAndFlush(reporte);
            log.info("Iniciando la generación del reporte pesado ID: {}", reporteId);

            // 2. Simulación de la lógica de generación (Ej: Consultar BD de libros, armar Excel o PDF)
            String urlArchivoGenerado = simularGeneracionDeArchivo(reporte);

            // 3. Si todo sale bien, guardamos la URL del archivo y marcamos como COMPLETADO
            reporte.setEstado(EstadoReporte.COMPLETADO);
            reporte.setUrlDescarga(urlArchivoGenerado);
            reporte.setFechaFinalizacion(LocalDateTime.now());
            log.info("Reporte ID: {} generado con éxito y subido a almacenamiento.", reporteId);

        } catch (Exception e) {
            log.error("Error crítico al generar el reporte ID: {}", reporteId, e);
            
            // 4. Si el proceso falla, guardamos el rastro del error para que el usuario sepa qué pasó
            reporte.setEstado(EstadoReporte.FALLIDO);
            reporte.setMensajeError("Fallo en la generación: " + e.getMessage());
            reporte.setFechaFinalizacion(LocalDateTime.now());
        }

        repository.save(reporte);
    }

    /**
     * Simula la creación del archivo físico y su subida a un servidor (como AWS S3).
     */
    private String simularGeneracionDeArchivo(Reporte reporte) throws InterruptedException {
        // Simulamos que tarda 5 segundos leyendo datos de la biblioteca y compilando un PDF/Excel
        Thread.sleep(5000); 

        // Aquí iría tu código real para generar el archivo usando librerías como Apache POI (Excel) o iText (PDF)
        // Y luego la subida a un almacenamiento en la nube.
        
        return "https://mi-almacenamiento-s3.amazonaws.com/reportes/" + reporte.getUsuarioId() + "-" + System.currentTimeMillis() + "." + reporte.getTipo().toString().toLowerCase();
    }
}