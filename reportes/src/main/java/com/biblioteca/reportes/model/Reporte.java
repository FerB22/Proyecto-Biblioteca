package com.biblioteca.reportes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "reportes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID autoincremental secuencial
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre; // Ej: "Reporte de Préstamos Vencidos"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoReporte tipo; // Enum: PDF, EXCEL, CSV

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoReporte estado; // Enum: PENDIENTE, PROCESANDO, COMPLETADO, FALLIDO

    @Column(name = "usuario_id", nullable = false, length = 50)
    private String usuarioId; // Quién solicitó el reporte

    @Column(name = "url_descarga", length = 500)
    private String urlDescarga; // URL pública o firmada (S3, MinIO) para descargar el archivo

    // Parámetros de filtrado que usó el usuario para generar el reporte
    // Guardado como JSONB en Postgres (Ej: {"fechaInicio": "2026-01-01", "categoria": "Sistemas"})
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> filtros;

    @Column(name = "fecha_solicitud", nullable = false, updatable = false)
    private LocalDateTime fechaSolicitud;

    @Column(name = "fecha_finalizacion")
    private LocalDateTime fechaFinalizacion;

    @Column(name = "mensaje_error", columnDefinition = "TEXT")
    private String mensajeError; // Por si el estado cambia a FALLIDO

    @PrePersist
    protected void onCreate() {
        this.fechaSolicitud = LocalDateTime.now();
        if (this.estado == null) {
            this.estado = EstadoReporte.PENDIENTE;
        }
    }
}