package br.com.diego.pscicologia.dominio.paciente;

import br.com.diego.pscicologia.comum.Moeda;
import br.com.diego.pscicologia.comum.Quantidade;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

public class PacienteTest {

    @Test
    void deveSerPossivelCriarUmPaciente() {
        String nomeEsperado = "Diego Guedes";
        String enderecoEsperado = "Av das Bananeiras";
        LocalDate dataDeInicioEsperado = LocalDate.now();
        Quantidade quantidaDeDiasNoMesEsperado = Quantidade.criar(2);
        Moeda valorPorSessaoEsperado = Moeda.criar(150);

        Paciente paciente = new Paciente(nomeEsperado, enderecoEsperado, quantidaDeDiasNoMesEsperado, valorPorSessaoEsperado);

        Assertions.assertThat(paciente.getNome()).isEqualTo(nomeEsperado);
        Assertions.assertThat(paciente.getEndereco()).isEqualTo(enderecoEsperado);
        Assertions.assertThat(paciente.getDataDeInicio()).isEqualTo(dataDeInicioEsperado);
        Assertions.assertThat(paciente.getQuantidaDeDiasNoMes()).isEqualTo(quantidaDeDiasNoMesEsperado);
        Assertions.assertThat(paciente.getValorPorSessao()).isEqualTo(valorPorSessaoEsperado);
    }

    @ParameterizedTest
    @MethodSource("dadosNecessariosParaValidar")
    void naoDeveSerPossivelCriarUmPacienteSemOsDadosNecessarios(String nome,
                                                                String endereco,
                                                                Quantidade quantidaDeDiasNoMes,
                                                                Moeda valorPorSessao,
                                                                String mensagemEsperada) {

        Throwable excecaoLancada = Assertions.catchThrowable(() -> new Paciente(nome, endereco, quantidaDeDiasNoMes, valorPorSessao));

        Assertions.assertThat(excecaoLancada).hasMessageContaining(mensagemEsperada);

    }

    private static Stream<Arguments> dadosNecessariosParaValidar() {
        String nome = "Teste";
        String endereco = "Endereço teste";
        Quantidade quantidaDeDiasNoMes = Quantidade.ZERO;
        Moeda valorPorSessao = Moeda.ZERO;

        return Stream.of(
                Arguments.of(null, endereco, quantidaDeDiasNoMes, valorPorSessao, "Não é possível criar um paciênte sem informar o nome."),
                Arguments.of(nome, null, quantidaDeDiasNoMes, valorPorSessao, "Não é possível criar um paciênte sem informar o endereço."),
                Arguments.of(nome, endereco, null, valorPorSessao, "Não é possível criar um paciênte sem quantidade de dias no mes."),
                Arguments.of(nome, endereco, quantidaDeDiasNoMes, null, "Não é possível criar um paciênte sem informar o valor por sessão.")
        );
    }
}