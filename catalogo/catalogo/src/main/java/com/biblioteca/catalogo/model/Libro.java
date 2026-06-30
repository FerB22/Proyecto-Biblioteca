package com.biblioteca.catalogo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "libros")
@Data
@NoArgsConstructor
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título del libro es obligatorio")
    private String titulo;

    @NotBlank(message = "El autor es obligatorio")
    private String autor;

    @NotBlank(message = "El código ISBN es obligatorio")
    @Column(unique = true)
    private String isbn;

    @NotBlank(message = "El tipo de material (Libro/Revista) es obligatorio")
    private String tipoMaterial;

    @Min(value = 0, message = "El stock disponible no puede ser negativo")
    private int stock;
}