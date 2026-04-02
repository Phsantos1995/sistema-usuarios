package com.br.sistema;

import com.br.sistema.dominio.Usuario;
import com.br.sistema.dto.request.UsuarioRequestDTO;
import com.br.sistema.dto.response.UsuarioResponseDTO;
import com.br.sistema.exception.custom.UsuarioNaoEncontradoException;
import com.br.sistema.repository.UsuarioRepository;
import com.br.sistema.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UsuarioService service;

    @Test
    void deveBuscarUsuarioPorIdComSucesso() {
        // Arrange
        Usuario usuario = new Usuario("João Silva");
        usuario.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(usuario));

        // Act
        UsuarioResponseDTO response = service.buscarPorID(1L);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("João Silva", response.getNome());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(UsuarioNaoEncontradoException.class, () -> {
            service.buscarPorID(999L);
        });

        verify(repository, times(1)).findById(999L);
    }

    @Test
    void deveSalvarUsuarioComSucesso() {
        UsuarioRequestDTO request = new UsuarioRequestDTO();
        request.setNome("Maria Oliveira");

        Usuario usuarioSalvo = new Usuario("Maria Oliveira");
        usuarioSalvo.setId(10L);

        when(repository.save(any(Usuario.class))).thenReturn(usuarioSalvo);

        UsuarioResponseDTO response = service.salvar(request);

        assertEquals(10L, response.getId());
        assertEquals("Maria Oliveira", response.getNome());
    }

    @Test
    void deveListarTodosUsuarios() {
        List<Usuario> usuarios = List.of(
                new Usuario("João"),
                new Usuario("Maria")
        );
        usuarios.get(0).setId(1L);
        usuarios.get(1).setId(2L);

        when(repository.findAll()).thenReturn(usuarios);

        List<UsuarioResponseDTO> responses = service.listarTodos();

        assertEquals(2, responses.size());
        assertEquals("João", responses.get(0).getNome());
    }

    @Test
    void deveDeletarUsuarioComSucesso() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        assertDoesNotThrow(() -> service.deletar(1L));
        verify(repository, times(1)).deleteById(1L);
    }
}