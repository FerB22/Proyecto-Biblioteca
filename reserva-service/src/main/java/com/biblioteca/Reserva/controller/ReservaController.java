package com.biblioteca.Reserva.controller;

import com.biblioteca.Reserva.dto.ReservaRequestDTO;
import com.biblioteca.Reserva.model.Reserva;
import com.biblioteca.Reserva.service.ReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    @PostMapping
    public ResponseEntity<Reserva> crearReserva(@Valid @RequestBody ReservaRequestDTO request) {
        Reserva creada = reservaService.crearReserva(request);
        return new ResponseEntity<>(creada, HttpStatus.CREATED);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Reserva>> obtenerReservasUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(reservaService.obtenerReservasUsuario(usuarioId));
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Reserva> cancelarReserva(@PathVariable Long id) {
        return ResponseEntity.ok(reservaService.cancelarReserva(id));
    }
}
