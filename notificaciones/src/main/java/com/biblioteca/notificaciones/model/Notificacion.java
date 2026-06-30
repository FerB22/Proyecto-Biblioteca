package com.biblioteca.notificaciones.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;

// Imports de Lombok que faltaban
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Imports de Hibernate para JSONB que faltaban
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Table(name = "notificacion")
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Spring Boot genera el UUID automáticamente
    private Long id;

    @Column(name = "correlation_id", length = 50)
    private String correlationId;

    @Column(nullable = false, length = 50)
    private String origen;

    @Column(name = "usuario_id", nullable = false, length = 50)
    private String usuarioId;

    @Column(nullable = false, length = 150)
    private String destino;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CanalNotificacion canal;

    @Column(name = "tipo_notificacion", nullable = false, length = 50)
    private String tipoNotificacion;

    private String titulo;
    
    @Column(columnDefinition = "TEXT") // Para mensajes largos o HTML
    private String contenido;

    @Column(name = "plantilla_id", length = 50)
    private String plantillaId;

    // Magia de Hibernate 6 para mapear automáticamente a JSONB en Postgres
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, String> parametros; 

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoNotificacion estado;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio;

    private int intentos = 0;

    @Column(name = "mensaje_error", columnDefinition = "TEXT")
    private String mensajeError;

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
        if (this.estado == null) {
            this.estado = EstadoNotificacion.PENDIENTE;
        }
    }
}


