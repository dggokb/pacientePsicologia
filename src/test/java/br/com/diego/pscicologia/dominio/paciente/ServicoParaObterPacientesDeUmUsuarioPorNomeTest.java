package br.com.diego.pscicologia.dominio.paciente;

import br.com.diego.pscicologia.builder.PacienteBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

class ServicoParaObterPacientesDeUmUsuarioPorNomeTest {

    private ServicoParaObterPacientesDeUmUsuarioPorNome servico;
    private PacienteRepositorio pacienteRepositorio;
    private String usuarioId;
    private String nome;

    @BeforeEach
    void setUp() {
        usuarioId = UUID.randomUUID().toString();
        pacienteRepositorio = Mockito.mock(PacienteRepositorio.class);
        servico = new ServicoParaObterPacientesDeUmUsuarioPorNome(pacienteRepositorio);
        nome = "João";
    }

    @Test
    void deveSerPossivelObterTodosOsPacientesDeUmUsuarioQuandoNaoForInformadoONome() {
        String nome = "";
        Paciente primeiroPaciente = new PacienteBuilder().comUsuarioId(usuarioId).comNome("Diego").criar();
        Paciente segundoPaciente = new PacienteBuilder().comUsuarioId(usuarioId).comNome("João").criar();
        Mockito.when(pacienteRepositorio.buscarDoUsuario(usuarioId)).thenReturn(Arrays.asList(primeiroPaciente, segundoPaciente));

        List<Paciente> pacientes = servico.obter(nome, usuarioId);

        Assertions.assertThat(pacientes).containsExactlyInAnyOrder(primeiroPaciente, segundoPaciente);
        Mockito.verify(pacienteRepositorio, Mockito.never()).buscarTodos(Mockito.anyString());
    }

    @Test
    void deveSerPossivelObterTodosOsPacientesQuandoNaoPossuirNome() {
        String nome = "";
        Paciente primeiroPaciente = new PacienteBuilder().comNome("Diego").criar();
        Paciente segundoPaciente = new PacienteBuilder().comNome("João").criar();
        Mockito.when(pacienteRepositorio.buscarDoUsuario(usuarioId)).thenReturn(Arrays.asList(primeiroPaciente, segundoPaciente));

        List<Paciente> pacientes = servico.obter(nome, usuarioId);

        Assertions.assertThat(pacientes).containsExactlyInAnyOrder(primeiroPaciente, segundoPaciente);
        Mockito.verify(pacienteRepositorio, Mockito.never()).buscarTodos(Mockito.anyString());
    }

    @Test
    void deveSerPossivelObterOsPacientesQuandoForInformadoONome() {
        Paciente paciente = new PacienteBuilder().comNome("Diego").criar();
        Mockito.when(pacienteRepositorio.buscarTodos(paciente.getNome(), paciente.getUsuarioId())).thenReturn(List.of(paciente));

        List<Paciente> pacientes = servico.obter(paciente.getNome(), paciente.getUsuarioId());

        Assertions.assertThat(pacientes).containsOnly(paciente);
        Mockito.verify(pacienteRepositorio, Mockito.never()).buscarDoUsuario(usuarioId);
    }

    @Test
    void deveRetornarVazioQuandoForInformadoONomeENaoPossuirONome() {
        Mockito.when(pacienteRepositorio.buscarTodos(nome)).thenReturn(Collections.emptyList());

        List<Paciente> pacientes = servico.obter(nome, usuarioId);

        Assertions.assertThat(pacientes).isEmpty();
        Mockito.verify(pacienteRepositorio, Mockito.never()).findAll();
    }

    @Test
    void naoDeveSerPossivelObterOsPacientesSenaoInformarOUsuario() {
        String usuarioId = null;

        Throwable excecaoLancada = Assertions.catchThrowable(() -> servico.obter(nome, usuarioId));

        Assertions.assertThat(excecaoLancada).hasMessageContaining("Não foi possível buscar os pacientes, pois não foi informado o usuário.");
    }
}