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

import java.util.Optional;
import java.util.UUID;

public class ConsultaPacientePorIdTest {

    private ConsultaPacientePorId consultaPacientePorId;
    private PacienteRepositorio pacienteRepositorio;
    private String id;
    private FiltroDeConsultaDePaciente filtro;

    @BeforeEach
    void setUp() {
        pacienteRepositorio = Mockito.mock(PacienteRepositorio.class);
        consultaPacientePorId = new ConsultaPacientePorIdConcreto(pacienteRepositorio);
        id = UUID.randomUUID().toString();
        filtro = new FiltroDeConsultaDePaciente().comId(id);
    }

    @Test
    void deveSerPossivelConsultarUmPaciente() throws Exception {
        Valor valor = new ValorBuilder().criar();
        Paciente paciente = new PacienteBuilder().comValores(valor).criar();
        Mockito.when(pacienteRepositorio.findById(id)).thenReturn(Optional.ofNullable(paciente));

        PacienteDTO dtoObtido = consultaPacientePorId.consultar(filtro);

        Assertions.assertThat(dtoObtido.id).isEqualTo(paciente.getId());
        Assertions.assertThat(dtoObtido.nome).isEqualTo(paciente.getNome());
        Assertions.assertThat(dtoObtido.endereco).isEqualTo(paciente.getEndereco());
        Assertions.assertThat(dtoObtido.dataDeInicio).isEqualTo(DateUtils.obterDataFormatada(paciente.getDataDeInicio()));
        Assertions.assertThat(dtoObtido.inativo).isEqualTo(paciente.getInativo());
        Assertions.assertThat(dtoObtido.tipo).isEqualTo(paciente.obterDescricaoDoTipo());
        Assertions.assertThat(dtoObtido.valores).extracting(valorDTO -> valorDTO.valorPorSessao).containsOnly(valor.getValorPorSessao().valor());
        Assertions.assertThat(dtoObtido.valores).extracting(valorDTO -> valorDTO.quantidaDeDiasNoMes).containsOnly(valor.getQuantidadeDeDiasNoMes().valor().intValue());
        Assertions.assertThat(dtoObtido.valores).extracting(valorDTO -> valorDTO.mes).containsOnly(valor.getMes().getDescricao());
        Assertions.assertThat(dtoObtido.valores).extracting(valorDTO -> valorDTO.ano).containsOnly(valor.getAno());
    }

    @Test
    void naoDeveSerPossivelConsultarUmPacienteSeNaoInformar() throws Exception {
        String mensagemEsperada = "É necessário informar o paciente para consulta.";
        FiltroDeConsultaDePaciente filtroSemId = new FiltroDeConsultaDePaciente();

        Throwable excecaoLancada = Assertions.catchThrowable(() -> consultaPacientePorId.consultar(filtroSemId));

        Assertions.assertThat(excecaoLancada).hasMessageContaining(mensagemEsperada);
    }

    @Test
    void naoDeveSerPossivelConsultarUmPacienteSeNaoForEncontradoOPaciente() throws Exception {
        String mensagemEsperada = "Não foi possível contrar o paciente.";
        Mockito.when(pacienteRepositorio.findById(id)).thenReturn(Optional.empty());

        Throwable excecaoLancada = Assertions.catchThrowable(() -> consultaPacientePorId.consultar(filtro));

        Assertions.assertThat(excecaoLancada).hasMessageContaining(mensagemEsperada);
    }
}