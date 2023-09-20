package br.com.diego.pscicologia.servico.paciente.consulta;

import br.com.diego.pscicologia.builder.PacienteBuilder;
import br.com.diego.pscicologia.dominio.paciente.Paciente;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MontadorDePacienteDTOTest {

    private MontadorDePacienteDTO montadorDTO;

    @BeforeEach
    void setUp() {
        montadorDTO = new MontadorDePacienteDTO();
    }

    @Test
    void deveSerPossivelMontarUmPacienteDTOSemAQuantidadeDeDiasNoMesSeForDoTipoFixo() throws Exception {
        Paciente paciente = new PacienteBuilder().criarTipoFixo();

        PacienteDTO dto = montadorDTO.montar(paciente);

        Assertions.assertThat(dto.id).isEqualTo(paciente.getId());
        Assertions.assertThat(dto.nome).isEqualTo(paciente.getNome());
        Assertions.assertThat(dto.endereco).isEqualTo(paciente.getEndereco());
        Assertions.assertThat(dto.dataDeInicio).isEqualTo(paciente.getDataDeInicio());
        Assertions.assertThat(dto.quantidaDeDiasNoMes).isNull();
        Assertions.assertThat(dto.valorPorSessao).isEqualTo(paciente.getValorPorSessao().valor());
        Assertions.assertThat(dto.inativo).isEqualTo(paciente.getInativo());
        Assertions.assertThat(dto.tipo).isEqualTo(paciente.obterDescricaoDoTipo());
    }

    @Test
    void deveSerPossivelMontarUmPacienteDTOComAQuantidadeDeDiasNoMesSeForDoTipoValorPorSessao() throws Exception {
        Paciente paciente = new PacienteBuilder().criarTipoValorPorSessao();

        PacienteDTO dto = montadorDTO.montar(paciente);

        Assertions.assertThat(dto.id).isEqualTo(paciente.getId());
        Assertions.assertThat(dto.nome).isEqualTo(paciente.getNome());
        Assertions.assertThat(dto.endereco).isEqualTo(paciente.getEndereco());
        Assertions.assertThat(dto.dataDeInicio).isEqualTo(paciente.getDataDeInicio());
        Assertions.assertThat(dto.quantidaDeDiasNoMes).isEqualTo(paciente.getQuantidadeDeDiasNoMes().valor().intValue());
        Assertions.assertThat(dto.valorPorSessao).isEqualTo(paciente.getValorPorSessao().valor());
        Assertions.assertThat(dto.inativo).isEqualTo(paciente.getInativo());
        Assertions.assertThat(dto.tipo).isEqualTo(paciente.obterDescricaoDoTipo());
    }

    @Test
    void naoDeveSerPossivelMontarUmPacienteDTOSeNaoInformarOPaciente() throws Exception {
        String mensagemEsperada = "É necessário informar o paciente para montar os dados da consulta.";
        Paciente paciente = null;

        Throwable excecaoLancada = Assertions.catchThrowable(() -> montadorDTO.montar(paciente));

        Assertions.assertThat(excecaoLancada).hasMessageContaining(mensagemEsperada);
    }
}