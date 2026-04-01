package com.br.sistema.controller;

import com.br.sistema.dto.request.UsuarioRequestDTO;
import com.br.sistema.dto.response.UsuarioResponseDTO;
import com.br.sistema.dto.response.UsuarioResumoDTO;
import com.br.sistema.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping
    public List<UsuarioResponseDTO> listar() {
        return service.listarTodos();
    }

    @PostMapping


    public ResponseEntity<UsuarioResponseDTO> criar(@Valid @RequestBody UsuarioRequestDTO request) {
        UsuarioResponseDTO response = service.salvar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorID(@PathVariable Long id) {
        UsuarioResponseDTO response = service.buscarPorID(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody UsuarioRequestDTO request) {
        UsuarioResponseDTO response = service.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/resumo")
    public List<UsuarioResumoDTO> resumo() {
        return service.listarResumo();
    }
}