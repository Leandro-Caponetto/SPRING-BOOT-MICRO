package com.msvc.usuario.exeptions;

import com.msvc.usuario.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExcptionController {

    @ExceptionHandler(ResourceNotFoundExeption.class)
    public ResponseEntity<ApiResponse> handlerResourceNotFoundException(ResourceNotFoundExeption resourceNotFoundExeption){
        String mensaje = resourceNotFoundExeption.getMessage();

        ApiResponse response = new ApiResponse().builder()
                .message(mensaje)
                .success(true)
                .status(HttpStatus.NOT_FOUND)
                .build();
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }
}
