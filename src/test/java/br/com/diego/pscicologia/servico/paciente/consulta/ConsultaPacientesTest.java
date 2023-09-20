package br.com.diego.pscicologia.servico.paciente.consulta;

import br.com.diego.pscicologia.builder.PacienteBuilder;
import br.com.diego.pscicologia.dominio.paciente.Paciente;
import br.com.diego.pscicologia.dominio.paciente.PacienteRepositorio;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class ConsultaPacientesTest {

    private PacienteRepositorio pacienteRepositorio;
    private ConsultaPacientes consultaPacientes;

    @BeforeEach
    void setUp() {
        pacienteRepositorio = Mockito.mock(PacienteRepositorio.class);
        consultaPacientes = new ConsultaPacientesConcreto(pacienteRepositorio);
    }

    @Test
    void deveSerPossivelConsultarTodosPacientesCadastrados() throws Exception {
        Paciente primeiroPaciente = new PacienteBuilder().criar();
        Paciente segundoPaciente = new PacienteBuilder().criar();
        List<Paciente> pacientes = Arrays.asList(primeiroPaciente, segundoPaciente);
        Mockito.when(pacienteRepositorio.findAll()).thenReturn(pacientes);

        List<PacienteDTO> dtos = consultaPacientes.buscarTodos();

        Assertions.assertThat(dtos).extracting(dto -> dto.id).containsExactlyInAnyOrder(primeiroPaciente.getId(),
                segundoPaciente.getId());
        Assertions.assertThat(dtos).extracting(dto -> dto.nome).containsExactlyInAnyOrder(primeiroPaciente.getNome(),
                segundoPaciente.getNome());
        Assertions.assertThat(dtos).extracting(dto -> dto.endereco).containsExactlyInAnyOrder(primeiroPaciente.getEndereco(),
                segundoPaciente.getEndereco());
        Assertions.assertThat(dtos).extracting(dto -> dto.quantidaDeDiasNoMes).containsExactlyInAnyOrder(primeiroPaciente.getQuantidaDeDiasNoMes().quantidade(),
                segundoPaciente.getQuantidaDeDiasNoMes().quantidade());
        Assertions.assertThat(dtos).extracting(dto -> dto.valorPorSessao).containsExactlyInAnyOrder(primeiroPaciente.getValorPorSessao().valor(),
                segundoPaciente.getValorPorSessao().valor());
        Assertions.assertThat(dtos).extracting(dto -> dto.dataDeInicio).containsExactlyInAnyOrder(primeiroPaciente.getDataDeInicio(),
                segundoPaciente.getDataDeInicio());
        Assertions.assertThat(dtos).extracting(dto -> dto.inativo).containsExactlyInAnyOrder(primeiroPaciente.getInativo(),
                segundoPaciente.getInativo());
        Assertions.assertThat(dtos).extracting(dto -> dto.tipo).containsExactlyInAnyOrder(primeiroPaciente.obterDescricaoDoTipo(),
                segundoPaciente.obterDescricaoDoTipo());
    }

    @Test
    void deveInformarMensagemSeNaoPossuirNenhumPacienteCadastrado() {
        Mockito.when(pacienteRepositorio.findAll()).thenReturn(Collections.emptyList());

        Throwable excecaoLancada = Assertions.catchThrowable(() -> consultaPacientes.buscarTodos());

        Assertions.assertThat(excecaoLancada).hasMessageContaining("Não foi possível encontrar nenhum paciente cadastrado.");
    }
}