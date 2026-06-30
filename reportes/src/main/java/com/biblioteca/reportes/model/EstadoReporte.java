package com.biblioteca.reportes.model;

public enum EstadoReporte {
    PENDIENTE,   // En cola para ser procesado
    PROCESANDO,  // El microservicio está armando el Excel/PDF
    COMPLETADO,  // Archivo generado y subido con éxito
    FALLIDO      // Ocurrió un error en la generación
}