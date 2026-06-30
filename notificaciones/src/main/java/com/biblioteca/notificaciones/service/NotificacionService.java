package com.biblioteca.notificaciones.service;

import com.biblioteca.notificaciones.model.Notificacion;
import com.biblioteca.notificaciones.model.EstadoNotificacion;
import com.biblioteca.notificaciones.repository.NotificacionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor // Genera el constructor para la inyección de dependencias de Lombok
public class NotificacionService {

    private final NotificacionRepository repository;
    // Aquí inyectarías tus clientes de proveedores externos (ej. EmailService, TwilioService)

    /**
     * Registra la notificación inicial en la base de datos.
     */
    @Transactional
    public Notificacion registrarNotificacion(Notificacion notificacion) {
        log.info("Registrando notificación para el usuario: {} desde el origen: {}", 
                notificacion.getUsuarioId(), notificacion.getOrigen());
        
        // El @PrePersist de la entidad se encargará de fijar la fecha y el estado PENDIENTE
        return repository.save(notificacion);
    }

    /**
     * Procesa y ejecuta el envío de la notificación.
     * Cambia los estados correspondientes y maneja los errores de resiliencia.
     */
    @Transactional
    public void enviarNotificacion(Long id) {



        Notificacion notificacion = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró la notificación con ID: " + id));

        // Evitar procesar notificaciones ya enviadas
        if (notificacion.getEstado() == EstadoNotificacion.ENVIADO) {
            log.warn("La notificación con ID {} ya fue enviada previamente.", id);
            return;
        }

        try {
            notificacion.setEstado(EstadoNotificacion.PROCESANDO);
            notificacion.setIntentos(notificacion.getIntentos() + 1);
            repository.saveAndFlush(notificacion); // Guardamos el estado intermedio

            // Simulación del envío según el canal
            ejecutarEnvioPorCanal(notificacion);

            // Si todo sale bien, marcamos como ENVIADO
            notificacion.setEstado(EstadoNotificacion.ENVIADO);
            notificacion.setFechaEnvio(LocalDateTime.now());
            notificacion.setMensajeError(null); // Limpiamos errores previos si los hubiera
            log.info("Notificación enviada con éxito. ID: {}", notificacion.getId());

        } catch (Exception e) {
            log.error("Error al enviar la notificación ID: {}. Intento número: {}", id, notificacion.getIntentos(), e);
            
            notificacion.setEstado(EstadoNotificacion.FALLIDO);
            notificacion.setMensajeError(e.getMessage());
            
            // Aquí podrías agregar lógica para evaluar si merece la pena reintentar 
            // o mandarlo directamente a una cola de errores (DLQ).
        }

        repository.save(notificacion);
    }

    /**
     * Lógica interna para decidir qué proveedor externo usar.
     */
    private void ejecutarEnvioPorCanal(Notificacion notificacion) throws Exception {
        switch (notificacion.getCanal()) {
            case EMAIL:
                log.info("Enviando Correo a {} usando plantilla {}", notificacion.getDestino(), notificacion.getPlantillaId());
                // emailProvider.send(notificacion.getDestino(), notificacion.getTitulo(), ...);
                break;
                
            case SMS:
                log.info("Enviando SMS a {}", notificacion.getDestino());
                // smsProvider.send(notificacion.getDestino(), notificacion.getContenido());
                break;
                
            case PUSH:
                log.info("Enviando Push Notification al token de dispositivo: {}", notificacion.getDestino());
                break;
                
            case WHATSAPP:
                log.info("Enviando mensaje de WhatsApp al número: {}", notificacion.getDestino());
                break;
                
            default:
                throw new IllegalArgumentException("Canal de notificación no soportado: " + notificacion.getCanal());
        }
        
        // Simulación de delay de red o llamada HTTP exitosa
        // Si quieres probar un fallo, puedes descomentar la siguiente línea:
        // throw new RuntimeException("Timeout al conectar con el proveedor de mensajería");
    }
}