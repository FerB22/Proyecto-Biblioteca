package com.biblioteca.notificaciones.controller;

import com.biblioteca.notificaciones.dto.NotificacionDTO;
import com.biblioteca.notificaciones.model.Notificacion;
import com.biblioteca.notificaciones.service.NotificacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/api/v1/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {

    private final NotificacionService service;

    @PostMapping
    public ResponseEntity<String> solicitarNotificacion(@Valid @RequestBody NotificacionDTO requestDto) {
        log.info("Recibida solicitud de notificación desde el servicio: {} para el usuario: {}", 
                requestDto.getOrigen(), requestDto.getUsuarioId());

        // 1. Convertimos el DTO a nuestra Entidad JPA manualmente
        Notificacion nuevaNotificacion = mapearDtoAEntidad(requestDto);

        // 2. Registramos de inmediato en PostgreSQL (Estado inicial: PENDIENTE)
        Notificacion registrada = service.registrarNotificacion(nuevaNotificacion);

        // 3. ¡Magia Asíncrona! Ejecutamos el envío en un hilo separado
        CompletableFuture.runAsync(() -> {
            try {
                service.enviarNotificacion(registrada.getId());
            } catch (Exception e) {
                log.error("Fallo catastrófico asíncrono para la notificación ID: {}", registrada.getId(), e);
            }
        });

        // 4. Respondemos rápido con HTTP 202
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body("Solicitud recibida. ID de seguimiento en Base de Datos: " + registrada.getId());
    }

    private Notificacion mapearDtoAEntidad(NotificacionDTO dto) {
        Notificacion entidad = new Notificacion();
        entidad.setCorrelationId(dto.getCorrelationId());
        entidad.setOrigen(dto.getOrigen());
        entidad.setUsuarioId(dto.getUsuarioId());
        entidad.setDestino(dto.getDestino());
        entidad.setCanal(dto.getCanal());
        entidad.setTipoNotificacion(dto.getTipoNotificacion());
        entidad.setTitulo(dto.getTitulo());
        entidad.setContenido(dto.getContenido());
        entidad.setPlantillaId(dto.getPlantillaId());
        entidad.setParametros(dto.getParametros());
        return entidad;
    }
}