package com.biblioteca.Reserva.service;

import com.biblioteca.Reserva.dto.ReservaRequestDTO;
import com.biblioteca.Reserva.model.Reserva;
import com.biblioteca.Reserva.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;

    public Reserva crearReserva(ReservaRequestDTO request) {
        Reserva nuevaReserva = Reserva.builder()
                .usuarioId(request.getUsuarioId())
                .libroId(request.getLibroId())
                .fechaReserva(request.getFechaReserva())
                .estado("PENDIENTE")
                .build();
        
        return reservaRepository.save(nuevaReserva);
    }

    public List<Reserva> obtenerReservasUsuario(Long usuarioId) {
        return reservaRepository.findByUsuarioId(usuarioId);
    }

    public Reserva cancelarReserva(Long reservaId) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        
        reserva.setEstado("CANCELADA");
        return reservaRepository.save(reserva);
    }
}
