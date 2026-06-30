package com.biblioteca.reportes.dto;

import com.biblioteca.reportes.model.TipoReporte;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Map;

public record ReporteRequestDto(
    @NotBlank(message = "El nombre del reporte es requerido")
    String nombre,

    @NotNull(message = "El tipo de reporte (PDF, EXCEL) es requerido")
    TipoReporte tipo,

    @NotBlank(message = "El ID del usuario es requerido")
    String usuarioId,

    Map<String, Object> filtros // Parámetros opcionales para filtrar datos
) {}