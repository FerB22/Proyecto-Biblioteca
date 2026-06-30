package com.biblioteca.catalogo.repository;

import com.biblioteca.catalogo.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    
    // Buscar libros que contengan parte del título (ignora mayúsculas/minúsculas)
    List<Libro> findByTituloContainingIgnoreCase(String titulo);

    // Buscar libros por autor
    List<Libro> findByAutorContainingIgnoreCase(String autor);
}