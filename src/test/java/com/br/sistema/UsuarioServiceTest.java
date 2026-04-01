
import com.br.sistema.service.UsuarioService;

import com.br.sistema.dominio.Usuario;
import com.br.sistema.dto.response.UsuarioResponseDTO;
import com.br.sistema.exception.custom.UsuarioNaoEncontradoException;
import com.br.sistema.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    void deveBuscarUsuarioPorId() {

        Usuario usuario = new Usuario("João");
        usuario.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(usuario));

        UsuarioResponseDTO response = service.buscarPorID(1L);

        assertEquals("João", response.getNome());
    }





}