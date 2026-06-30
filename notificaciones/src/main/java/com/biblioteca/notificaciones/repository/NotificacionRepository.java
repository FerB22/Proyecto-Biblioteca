package com.biblioteca.notificaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biblioteca.notificaciones.model.Notificacion;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

}
