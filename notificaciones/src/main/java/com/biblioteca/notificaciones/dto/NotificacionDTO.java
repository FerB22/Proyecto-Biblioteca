package com.biblioteca.notificaciones.dto;

import com.biblioteca.notificaciones.model.CanalNotificacion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificacionDTO {

private String correlationId;

    @NotBlank(message = "El origen es requerido")
    private String origen;

    @NotBlank(message = "El usuarioId es requerido")
    private String usuarioId;

    @NotBlank(message = "El destino es requerido")
    private String destino;

    @NotNull(message = "El canal es requerido")
    private CanalNotificacion canal;

    @NotBlank(message = "El tipo de notificación es requerido")
    private String tipoNotificacion;

    private String titulo;
    private String contenido;
    private String plantillaId;
    private Map<String, String> parametros;
}