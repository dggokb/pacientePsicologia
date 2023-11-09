package br.com.diego.psicologia.dominio.usuario;

import br.com.diego.psicologia.builder.UsuarioBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

class UsuarioTest {

    private String username;
    private String password;
    private TipoDeUsuario role;

    @BeforeEach
    void setUp() {
        username = "Diego";
        password = UUID.randomUUID().toString();
        role = TipoDeUsuario.ADMIN;
    }

    @Test
    void deveSerPossivelCriarUmUsuario() {
        String username = "Diego";
        String password = UUID.randomUUID().toString();
        TipoDeUsuario role = TipoDeUsuario.ADMIN;

        Usuario usuario = new Usuario(username, password, role);

        Assertions.assertThat(usuario.getUsername()).isEqualTo(username);
        Assertions.assertThat(usuario.getPassword()).isEqualTo(password);
        Assertions.assertThat(usuario.getRole()).isEqualTo(role);
        Assertions.assertThat(usuario.isEnabled()).isTrue();
    }

    @Test
    void deveSerPossivelCriarUmUsuarioSemContaExpirada() {
        Usuario usuario = new Usuario(username, password, role);

        Assertions.assertThat(usuario.isAccountNonExpired()).isTrue();
    }

    @Test
    void deveSerPossivelCriarUmUsuarioSemContaBloqueada() {
        Usuario usuario = new Usuario(username, password, role);

        Assertions.assertThat(usuario.isAccountNonLocked()).isTrue();
    }

    @Test
    void deveSerPossivelCriarUmUsuarioSemCredencialExpirada() {
        Usuario usuario = new Usuario(username, password, role);

        Assertions.assertThat(usuario.isCredentialsNonExpired()).isTrue();
    }

    @ParameterizedTest
    @MethodSource("dadosNecessariosParaValidarCriacao")
    void naoDeveSerPossivelCriarUmPacienteDeValorMensalSemOsDadosNecessarios(String username,
                                                                             String password,
                                                                             TipoDeUsuario tipoDeUsuario,
                                                                             String mensagemEsperada) {

        Throwable excecaoLancada = Assertions.catchThrowable(() -> new Usuario(username, password, tipoDeUsuario));

        Assertions.assertThat(excecaoLancada).hasMessageContaining(mensagemEsperada);
    }

    private static Stream<Arguments> dadosNecessariosParaValidarCriacao() {
        Usuario usuario = new UsuarioBuilder().criar();

        return Stream.of(
                Arguments.of(null, usuario.getPassword(), usuario.getRole(), "Não é possível criar um usuário sem informar o username."),
                Arguments.of(usuario.getUsername(), null, usuario.getRole(), "Não é possível criar um usuário sem informar o password."),
                Arguments.of(usuario.getUsername(), usuario.getPassword(), null, "Não é possível criar um usuário sem informar o tipo de usuário.")
        );
    }
}