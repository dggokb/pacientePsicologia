package br.com.diego.pscicologia.dominio.paciente;

import br.com.diego.pscicologia.builder.PacienteBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class ServicoParaObterPacientesPorNomeTest {

    private ServicoParaObterPacientesPorNome servico;
    private PacienteRepositorio pacienteRepositorio;

    @BeforeEach
    void setUp() {
        pacienteRepositorio = Mockito.mock(PacienteRepositorio.class);
        servico = new ServicoParaObterPacientesPorNome(pacienteRepositorio);
    }

    @Test
    void deveSerPossivelObterTodosOsPacientesQuandoNaoForInformadoONome() {
        String nome = "";
        Paciente primeiroPaciente = new PacienteBuilder().comNome("Diego").criar();
        Paciente segundoPaciente = new PacienteBuilder().comNome("João").criar();
        Mockito.when(pacienteRepositorio.findAll()).thenReturn(Arrays.asList(primeiroPaciente, segundoPaciente));

        List<Paciente> pacientes = servico.obter(nome);

        Assertions.assertThat(pacientes).containsExactlyInAnyOrder(primeiroPaciente, segundoPaciente);
        Mockito.verify(pacienteRepositorio, Mockito.never()).buscarTodos(Mockito.anyString());
    }

    @Test
    void deveSerPossivelObterTodosOsPacientesQuandoNaoPossuirNome() {
        String nome = "";
        Paciente primeiroPaciente = new PacienteBuilder().comNome("Diego").criar();
        Paciente segundoPaciente = new PacienteBuilder().comNome("João").criar();
        Mockito.when(pacienteRepositorio.findAll()).thenReturn(Arrays.asList(primeiroPaciente, segundoPaciente));

        List<Paciente> pacientes = servico.obter(nome);

        Assertions.assertThat(pacientes).containsExactlyInAnyOrder(primeiroPaciente, segundoPaciente);
        Mockito.verify(pacienteRepositorio, Mockito.never()).buscarTodos(Mockito.anyString());
    }

    @Test
    void deveSerPossivelObterOsPacientesQuandoForInformadoONome() {
        Paciente paciente = new PacienteBuilder().comNome("Diego").criar();
        Mockito.when(pacienteRepositorio.buscarTodos(paciente.getNome())).thenReturn(List.of(paciente));

        List<Paciente> pacientes = servico.obter(paciente.getNome());

        Assertions.assertThat(pacientes).containsOnly(paciente);
        Mockito.verify(pacienteRepositorio, Mockito.never()).findAll();
    }

    @Test
    void deveRetornarVazioQuandoForInformadoONomeENaoPossuirONome() {
        String nome = "João";
        Mockito.when(pacienteRepositorio.buscarTodos(nome)).thenReturn(Collections.emptyList());

        List<Paciente> pacientes = servico.obter(nome);

        Assertions.assertThat(pacientes).isEmpty();
        Mockito.verify(pacienteRepositorio, Mockito.never()).findAll();
    }
}