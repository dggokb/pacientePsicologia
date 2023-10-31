package br.com.diego.pscicologia.servico.paciente.consulta;

import br.com.diego.pscicologia.builder.PacienteBuilder;
import br.com.diego.pscicologia.builder.ValorBuilder;
import br.com.diego.pscicologia.comum.DateUtils;
import br.com.diego.pscicologia.dominio.paciente.Paciente;
import br.com.diego.pscicologia.dominio.paciente.ServicoParaObterPacientesDeUmUsuarioPorNome;
import br.com.diego.pscicologia.dominio.paciente.Valor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

class ConsultaPacienteTest {
    private ServicoParaObterPacientesDeUmUsuarioPorNome servicoParaObterPacientesDeUmUsuarioPorNome;
    private ConsultaPaciente consultaPaciente;
    private String nome;
    private FiltroDeConsultaDePaciente filtro;

    @BeforeEach
    void setUp() {
        servicoParaObterPacientesDeUmUsuarioPorNome = Mockito.mock(ServicoParaObterPacientesDeUmUsuarioPorNome.class);
        consultaPaciente = new ConsultaPacienteConcreto(servicoParaObterPacientesDeUmUsuarioPorNome);
        nome = "Esteban Manuel";
        filtro = new FiltroDeConsultaDePaciente().comNome(nome);
    }

    @Test
    void deveSerPossivelConsultarUmPaciente() throws Exception {
        Valor valor = new ValorBuilder().criar();
        Paciente paciente = new PacienteBuilder().criar();
        Mockito.when(servicoParaObterPacientesDeUmUsuarioPorNome.obter(nome, filtro.getUsuarioId())).thenReturn(Collections.singletonList(paciente));

        List<PacienteDTO> dtoObtido = consultaPaciente.consultar(filtro);

        Assertions.assertThat(dtoObtido).extracting(dto -> dto.id).containsOnly(paciente.getId());
        Assertions.assertThat(dtoObtido).extracting(dto -> dto.nome).containsOnly(paciente.getNome());
        Assertions.assertThat(dtoObtido).extracting(dto -> dto.endereco).containsOnly(paciente.getEndereco());
        Assertions.assertThat(dtoObtido).extracting(dto -> dto.dataDeInicio).containsOnly(DateUtils.obterDataFormatada(paciente.getDataDeInicio()));
        Assertions.assertThat(dtoObtido).extracting(dto -> dto.inativo).containsOnly(paciente.getInativo());
        Assertions.assertThat(dtoObtido).extracting(dto -> dto.tipo).containsOnly(paciente.obterDescricaoDoTipo());
        Assertions.assertThat(dtoObtido).flatExtracting(dto -> dto.valores).extracting(valorDTO -> valorDTO.valorPorSessao).containsOnly(valor.getValorPorSessao().valor());
        Assertions.assertThat(dtoObtido).flatExtracting(dto -> dto.valores).extracting(valorDTO -> valorDTO.quantidaDeDiasNoMes).containsOnly(valor.getQuantidadeDeDiasNoMes().valor().intValue());
        Assertions.assertThat(dtoObtido).flatExtracting(dto -> dto.valores).extracting(valorDTO -> valorDTO.mes).containsOnly(valor.getMes().getDescricao());
        Assertions.assertThat(dtoObtido).flatExtracting(dto -> dto.valores).extracting(valorDTO -> valorDTO.ano).containsOnly(valor.getAno());
    }

    @Test
    void naoDeveSerPossivelConsultarUmPacienteSeNaoForEncontradoOPaciente() throws Exception {
        Mockito.when(servicoParaObterPacientesDeUmUsuarioPorNome.obter(nome, filtro.getUsuarioId())).thenReturn(Collections.emptyList());

        List<PacienteDTO> dtoObtido = consultaPaciente.consultar(filtro);

        Assertions.assertThat(dtoObtido).isEmpty();
    }
}