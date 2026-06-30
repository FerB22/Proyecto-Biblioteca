package com.biblioteca.notificaciones.model;

public enum EstadoNotificacion {
    PENDIENTE,   // La notificación fue registrada pero aún no se procesa
    PROCESANDO,  // Se está intentando conectar con el proveedor externo (ej. Twilio, SendGrid)
    ENVIADO,     // El proveedor confirmó la recepción del mensaje
    FALLIDO      // Agotó los intentos de reenvío y no pudo entregarse
}