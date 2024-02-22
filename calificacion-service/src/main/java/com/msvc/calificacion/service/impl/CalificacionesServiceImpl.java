package com.msvc.calificacion.service.impl;

import com.msvc.calificacion.entity.Calificacion;
import com.msvc.calificacion.repository.CalificacionRepository;
import com.msvc.calificacion.service.CalificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalificacionesServiceImpl implements CalificacionService {

    @Autowired
    private CalificacionRepository calificacionRepository;


    @Override
    public Calificacion create(Calificacion calificacion) {
        return calificacionRepository.save(calificacion);
    }

    @Override
    public List<Calificacion> getCalificaciones() {
        return calificacionRepository.findAll();
    }

    @Override
    public List<Calificacion> getCalificacioneByUsuarioId(String usuarioId) {
        return calificacionRepository.findByUsuarioId(usuarioId);
    }

    @Override
    public List<Calificacion> getCalificacioneByHotelId(String hotelId) {
        return calificacionRepository.findByHotelId(hotelId);
    }
}
