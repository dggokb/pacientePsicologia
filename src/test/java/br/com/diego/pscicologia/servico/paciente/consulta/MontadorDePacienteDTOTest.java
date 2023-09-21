package br.com.diego.pscicologia.servico.paciente.consulta;

import br.com.diego.pscicologia.builder.PacienteBuilder;
import br.com.diego.pscicologia.builder.ValorBuilder;
import br.com.diego.pscicologia.dominio.paciente.Paciente;
import br.com.diego.pscicologia.dominio.paciente.Tipo;
import br.com.diego.pscicologia.dominio.paciente.Valor;
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
    void deveSerPossivelMontarUmPacienteDTOLevenadoEmConsideracaoOTipoParaDevolverQuantidadeDeDiasNoMes() throws Exception {
        Valor primeiroValor = new ValorBuilder().criar();
        Valor segundoValor = new ValorBuilder().comTipo(Tipo.VALOR_FIXO).comQuantidadeDeDiasNoMes(null).criar();
        Paciente paciente = new PacienteBuilder().comValores(primeiroValor, segundoValor).criar();

        PacienteDTO dto = montadorDTO.montar(paciente);

        Assertions.assertThat(dto.id).isEqualTo(paciente.getId());
        Assertions.assertThat(dto.nome).isEqualTo(paciente.getNome());
        Assertions.assertThat(dto.endereco).isEqualTo(paciente.getEndereco());
        Assertions.assertThat(dto.dataDeInicio).isEqualTo(paciente.getDataDeInicio());
        Assertions.assertThat(dto.inativo).isEqualTo(paciente.getInativo());
        Assertions.assertThat(dto.tipo).isEqualTo(paciente.obterDescricaoDoTipo());
        Assertions.assertThat(dto.valores).extracting(valorDTO -> valorDTO.valorPorSessao).containsOnly(primeiroValor.getValorPorSessao().valor(),
                segundoValor.getValorPorSessao().valor());
        Assertions.assertThat(dto.valores).extracting(valorDTO -> valorDTO.quantidaDeDiasNoMes).containsOnly(primeiroValor.getQuantidadeDeDiasNoMes().valor().intValue(),
                null);
        Assertions.assertThat(dto.valores).extracting(valorDTO -> valorDTO.mes).containsOnly(primeiroValor.getMes().getDescricao(),
                segundoValor.getMes().getDescricao());
        Assertions.assertThat(dto.valores).extracting(valorDTO -> valorDTO.ano).containsOnly(primeiroValor.getAno(),
                segundoValor.getAno());
    }

    @Test
    void naoDeveSerPossivelMontarUmPacienteDTOSeNaoInformarOPaciente() throws Exception {
        String mensagemEsperada = "É necessário informar o paciente para montar os dados da consulta.";
        Paciente paciente = null;

        Throwable excecaoLancada = Assertions.catchThrowable(() -> montadorDTO.montar(paciente));

        Assertions.assertThat(excecaoLancada).hasMessageContaining(mensagemEsperada);
    }
}