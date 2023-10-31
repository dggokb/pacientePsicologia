package br.com.diego.pscicologia.servico.paciente.consulta;

import br.com.diego.pscicologia.builder.PacienteBuilder;
import br.com.diego.pscicologia.builder.ValorBuilder;
import br.com.diego.pscicologia.comum.DateUtils;
import br.com.diego.pscicologia.dominio.paciente.Paciente;
import br.com.diego.pscicologia.dominio.paciente.PacienteRepositorio;
import br.com.diego.pscicologia.dominio.paciente.Valor;
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
    private FiltroDeConsultaDePaciente filtro;

    @BeforeEach
    void setUp() {
        pacienteRepositorio = Mockito.mock(PacienteRepositorio.class);
        consultaPacientes = new ConsultaPacientesConcreto(pacienteRepositorio);
        filtro = new FiltroDeConsultaDePaciente();
    }

    @Test
    void deveSerPossivelConsultarTodosPacientesCadastrados() throws Exception {
        Valor valorDoPrimeiroPaciente = new ValorBuilder().criar();
        Paciente primeiroPaciente = new PacienteBuilder().comValores(valorDoPrimeiroPaciente).criar();
        Valor valorDoSegundoPaciente = new ValorBuilder().criar();
        Paciente segundoPaciente = new PacienteBuilder().comValores(valorDoSegundoPaciente).criar();
        List<Paciente> pacientes = Arrays.asList(primeiroPaciente, segundoPaciente);
        Mockito.when(pacienteRepositorio.findAll()).thenReturn(pacientes);

        List<PacienteDTO> dtos = consultaPacientes.consultar(filtro);

        Assertions.assertThat(dtos).extracting(dto -> dto.id).containsExactlyInAnyOrder(primeiroPaciente.getId(),
                segundoPaciente.getId());
        Assertions.assertThat(dtos).extracting(dto -> dto.nome).containsExactlyInAnyOrder(primeiroPaciente.getNome(),
                segundoPaciente.getNome());
        Assertions.assertThat(dtos).extracting(dto -> dto.endereco).containsExactlyInAnyOrder(primeiroPaciente.getEndereco(),
                segundoPaciente.getEndereco());
        Assertions.assertThat(dtos).flatExtracting(dto -> dto.valores).extracting(valorDTO -> valorDTO.ano)
                .containsOnly(valorDoPrimeiroPaciente.getAno(), valorDoSegundoPaciente.getAno());
        Assertions.assertThat(dtos).flatExtracting(dto -> dto.valores).extracting(valorDTO -> valorDTO.mes)
                .containsOnly(valorDoPrimeiroPaciente.getMes().getDescricao(), valorDoSegundoPaciente.getMes().getDescricao());
        Assertions.assertThat(dtos).flatExtracting(dto -> dto.valores).extracting(valorDTO -> valorDTO.valorPorSessao)
                .containsOnly(valorDoPrimeiroPaciente.getValorPorSessao().valor(), valorDoSegundoPaciente.getValorPorSessao().valor());
        Assertions.assertThat(dtos).flatExtracting(dto -> dto.valores).extracting(valorDTO -> valorDTO.quantidaDeDiasNoMes)
                .containsOnly(valorDoPrimeiroPaciente.getQuantidadeDeDiasNoMes().quantidade(), valorDoSegundoPaciente.getQuantidadeDeDiasNoMes().quantidade());
        Assertions.assertThat(dtos).extracting(dto -> dto.dataDeInicio).containsExactlyInAnyOrder(DateUtils.obterDataFormatada(primeiroPaciente.getDataDeInicio()),
                DateUtils.obterDataFormatada(segundoPaciente.getDataDeInicio()));
        Assertions.assertThat(dtos).extracting(dto -> dto.inativo).containsExactlyInAnyOrder(primeiroPaciente.getInativo(),
                segundoPaciente.getInativo());
        Assertions.assertThat(dtos).extracting(dto -> dto.tipo).containsExactlyInAnyOrder(primeiroPaciente.obterDescricaoDoTipo(),
                segundoPaciente.obterDescricaoDoTipo());
    }

    @Test
    void deveInformarMensagemSeNaoPossuirNenhumPacienteCadastrado() {
        Mockito.when(pacienteRepositorio.findAll()).thenReturn(Collections.emptyList());

        Throwable excecaoLancada = Assertions.catchThrowable(() -> consultaPacientes.consultar(filtro));

        Assertions.assertThat(excecaoLancada).hasMessageContaining("Não foi possível encontrar nenhum paciente cadastrado.");
    }
}