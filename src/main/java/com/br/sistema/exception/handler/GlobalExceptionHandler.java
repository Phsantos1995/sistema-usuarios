package com.br.sistema.exception.handler;

import com.br.sistema.exception.custom.UsuarioNaoEncontradoException;
import com.br.sistema.exception.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleUsuarioNaoEncontrado(UsuarioNaoEncontradoException ex) {

        ErrorResponse error = new ErrorResponse(404, ex.getMessage());

        return ResponseEntity.status(404).body(error);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleGenerico(RuntimeException ex) {

        ErrorResponse error = new ErrorResponse(500, "Erro");

        return ResponseEntity.status(500).body(error);
    }
}

