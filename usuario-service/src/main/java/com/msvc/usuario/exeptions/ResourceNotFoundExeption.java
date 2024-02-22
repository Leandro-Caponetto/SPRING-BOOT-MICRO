package com.msvc.usuario.exeptions;

public class ResourceNotFoundExeption extends RuntimeException {

    public ResourceNotFoundExeption() {
        super("Recurso no encontrado en el servidor");
    }

    public ResourceNotFoundExeption(String message) {
        super(message);
    }
}
