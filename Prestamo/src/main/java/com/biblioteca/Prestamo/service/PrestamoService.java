package com.biblioteca.Prestamo.service;

import com.biblioteca.Prestamo.dto.PrestamoRequestDTO;
import com.biblioteca.Prestamo.model.Prestamo;
import com.biblioteca.Prestamo.repository.PrestamoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;

    public Prestamo crearPrestamo(PrestamoRequestDTO request) {
        Prestamo nuevoPrestamo = Prestamo.builder()
                .usuarioId(request.getUsuarioId())
                .libroId(request.getLibroId())
                .fechaPrestamo(LocalDate.now())
                .fechaDevolucionEsperada(request.getFechaDevolucionEsperada())
                .estado("ACTIVO")
                .build();
        
        return prestamoRepository.save(nuevoPrestamo);
    }

    public List<Prestamo> obtenerPrestamosPorUsuario(Long usuarioId) {
        return prestamoRepository.findByUsuarioId(usuarioId);
    }

    public Prestamo devolverLibro(Long prestamoId) {
        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado")); // Idealmente crear una Excepción personalizada
        
        prestamo.setFechaDevolucionReal(LocalDate.now());
        prestamo.setEstado("DEVUELTO");
        return prestamoRepository.save(prestamo);
    }
}
