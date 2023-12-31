package br.com.diego.psicologia.servico.autenticacaodeusuario.usuario;

import br.com.diego.psicologia.builder.UsuarioBuilder;
import br.com.diego.psicologia.dominio.usuario.TipoDeUsuario;
import br.com.diego.psicologia.dominio.usuario.Usuario;
import br.com.diego.psicologia.dominio.usuario.UsuarioRepositorio;
import br.com.diego.psicologia.servico.autenticacao.usuario.AdicionaUsuario;
import br.com.diego.psicologia.servico.autenticacao.usuario.AdicionaUsuarioConcreto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Optional;

class AdicionaUsuarioTest {

    private UsuarioRepositorio usuarioRepositorio;
    private AdicionaUsuario adicionaUsuario;

    @BeforeEach
    void setUp() {
        usuarioRepositorio = Mockito.mock(UsuarioRepositorio.class);
        adicionaUsuario = new AdicionaUsuarioConcreto(usuarioRepositorio);
    }

    @Test
    void deveSerPossivelAdicionarUmUsuario() {
        String username = "Diego";
        String password = "123456";
        String role = TipoDeUsuario.ADMIN.name();
        Mockito.when(usuarioRepositorio.finByUsername(username)).thenReturn(Optional.empty());
        ArgumentCaptor<Usuario> usuarioArgumentCaptor = ArgumentCaptor.forClass(Usuario.class);

        String identificadorDeRetorno = adicionaUsuario.executar(username, password, role);

        Mockito.verify(usuarioRepositorio).save(usuarioArgumentCaptor.capture());
        Assertions.assertThat(identificadorDeRetorno).isEqualTo(usuarioArgumentCaptor.getValue().getId());
    }

    @Test
    void naoDeveSerPossivelAdicionarUmUsuarioQueJahFoiAdicionado() {
        Usuario usuario = new UsuarioBuilder().criar();
        String mensagemEsperada = String.format("Usuário %s já cadastrado.", usuario.getUsername());
        Mockito.when(usuarioRepositorio.finByUsername(usuario.getUsername())).thenReturn(Optional.of(usuario));

        Throwable excecaoLancada = Assertions.catchThrowable(() -> adicionaUsuario.executar(usuario.getUsername(), usuario.getPassword(), usuario.getRole().name()));

        Assertions.assertThat(excecaoLancada).hasMessageContaining(mensagemEsperada);
    }
}