package com.example.microservicio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.microservicio.model.Empleados;
import com.example.microservicio.model.Productos;

// Repositorio correspondiente a las operaciones contra la tabla contenedora de los mensajes en la base de datos, contiene tanto operaciones genéricas 
// por defecto como algunas específicas creadas para el microservicio
@Repository
public interface MensajeRepository extends JpaRepository<Empleados, Long> {
    // Método que devuelve todos los mensajes entre dos usuarios pasados por parámetro
    @Query("SELECT m FROM Mensaje m WHERE (m.remitente.id = :usuario1Id AND m.destinatario.id = :usuario2Id) OR (m.remitente.id = :usuario2Id AND m.destinatario.id = :usuario1Id) ORDER BY m.fechaHora ASC")
    List<Empleados> findByRemitenteIdAndDestinatarioIdOrRemitenteIdAndDestinatarioIdOrderByFechaHoraAsc(Long usuario1Id, Long usuario2Id);

    // Método que devuelve todos los usuarios que le han enviado un mensaje al usuario pasado por parámetro 
    @Query("SELECT DISTINCT m.remitente FROM Mensaje m WHERE m.destinatario.id = :usuarioId")
    List<Productos> findRemitentesByUsuarioId(Long usuarioId);

    // Método que devuelve todos los usuarios a los cuales les ha enviado un mensaje el usuario pasado por parámetro 
    @Query("SELECT DISTINCT m.destinatario FROM Mensaje m WHERE m.remitente.id = :usuarioId")
    List<Productos> findDestinatariosByUsuarioId(Long usuarioId);

    // Método que marca como leídos todos los mensajes que un usuario concreto le ha mandado a otro
    @Modifying
    @Query("UPDATE Mensaje m SET m.leido = true WHERE (m.remitente.id = :usuario1Id AND m.destinatario.id = :usuario2Id) AND m.leido = false")
    void marcarMensajesComoLeidos(Long usuario1Id, Long usuario2Id);
}
