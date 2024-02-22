package com.msvc.usuario.service.impl;

import com.msvc.usuario.entity.Calificacion;
import com.msvc.usuario.entity.Hotel;
import com.msvc.usuario.entity.Usuario;
import com.msvc.usuario.exeptions.ResourceNotFoundExeption;
import com.msvc.usuario.repository.UsuarioRepository;
import com.msvc.usuario.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.SocketOption;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Override
    public Usuario saveUsuario(Usuario usuario) {
        String randomUsuarioId = UUID.randomUUID().toString();
        usuario.setUsuarioId(randomUsuarioId);
        return usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario getUsuario(String usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new  ResourceNotFoundExeption("Usuario no encontrado con el ID: " + usuarioId));


        Calificacion[] calificacionesDelUsuario = restTemplate.getForObject("http://CALIFICACION-SERVICE/calificaciones/usuarios/"+ usuario.getUsuarioId(), Calificacion[].class);

        List<Calificacion> calificaciones = Arrays.stream(calificacionesDelUsuario).collect(Collectors.toList());

        List<Calificacion> listaCalificaciones = calificaciones.stream().map(calificacion -> {
            System.out.println(calificacion.getHotel());
            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hoteles/"+calificacion.getHotelId(),Hotel.class);
            Hotel hotel = forEntity.getBody();
            logger.info("Respuesta con codigo de estado : {}", forEntity.getStatusCode());

            calificacion.setHotel(hotel);
            return calificacion;
        }).collect(Collectors.toList());



        usuario.setCalificaciones(listaCalificaciones);

        return usuario;
    }
}
