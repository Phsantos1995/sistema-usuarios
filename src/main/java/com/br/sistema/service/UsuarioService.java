package com.br.sistema.service;

import com.br.sistema.dominio.Usuario;
import com.br.sistema.dto.request.UsuarioRequestDTO;
import com.br.sistema.dto.response.UsuarioResponseDTO;
import com.br.sistema.dto.response.UsuarioResumoDTO;
import com.br.sistema.exception.custom.UsuarioNaoEncontradoException;
import com.br.sistema.repository.UsuarioRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public UsuarioResponseDTO salvar(UsuarioRequestDTO request) {
        // Cria a entidade diretamente com o nome vindo do DTO
        Usuario usuario = new Usuario(request.getNome());

        Usuario salvo = repository.save(usuario);

        return new UsuarioResponseDTO(salvo.getId(), salvo.getNome());
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new UsuarioNaoEncontradoException(id);
        }
        repository.deleteById(id);
    }

    public List<UsuarioResponseDTO> listarTodos() {
        return repository.findAll().stream()
                .map(u -> new UsuarioResponseDTO(u.getId(), u.getNome()))
                .toList();
    }

    public UsuarioResponseDTO buscarPorID(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));

        return new UsuarioResponseDTO(usuario.getId(), usuario.getNome());
    }

    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO request) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));

        usuario.setNome(request.getNome());

        Usuario salvo = repository.save(usuario);
        return new UsuarioResponseDTO(salvo.getId(), salvo.getNome());
    }

    public List<UsuarioResumoDTO> listarResumo() {
        return repository.findAll().stream()
                .map(usuario -> new UsuarioResumoDTO(usuario.getId(), usuario.getNome()))
                .toList();
    }
}