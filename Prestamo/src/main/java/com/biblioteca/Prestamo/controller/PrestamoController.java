package com.biblioteca.Prestamo.controller;

import com.biblioteca.Prestamo.dto.PrestamoRequestDTO;
import com.biblioteca.Prestamo.model.Prestamo;
import com.biblioteca.Prestamo.service.PrestamoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
@RequiredArgsConstructor
public class PrestamoController {

    private final PrestamoService prestamoService;

    @PostMapping
    public ResponseEntity<Prestamo> crearPrestamo(@Valid @RequestBody PrestamoRequestDTO request) {
        Prestamo creado = prestamoService.crearPrestamo(request);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Prestamo>> obtenerPrestamosUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(prestamoService.obtenerPrestamosPorUsuario(usuarioId));
    }

    @PutMapping("/{id}/devolucion")
    public ResponseEntity<Prestamo> registrarDevolucion(@PathVariable Long id) {
        return ResponseEntity.ok(prestamoService.devolverLibro(id));
    }
}
