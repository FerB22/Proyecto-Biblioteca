package com.biblioteca.Prestamo.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class PrestamoRequestDTO {

    @NotNull(message = "El ID del usuario es obligatorio")
    private Long usuarioId;

    @NotNull(message = "El ID del libro es obligatorio")
    private Long libroId;

    @NotNull(message = "La fecha de devolución es obligatoria")
    @Future(message = "La fecha de devolución debe ser en el futuro")
    private LocalDate fechaDevolucionEsperada;
}
