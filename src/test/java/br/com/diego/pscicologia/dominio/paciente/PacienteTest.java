package br.com.diego.pscicologia.dominio.paciente;

import br.com.diego.pscicologia.builder.PacienteBuilder;
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
        Tipo tipoDePacienteEsperado = Tipo.VALOR_MENSAL;

        Paciente paciente = new Paciente(nomeEsperado, enderecoEsperado, quantidaDeDiasNoMesEsperado, valorPorSessaoEsperado, tipoDePacienteEsperado);

        Assertions.assertThat(paciente.getNome()).isEqualTo(nomeEsperado);
        Assertions.assertThat(paciente.getEndereco()).isEqualTo(enderecoEsperado);
        Assertions.assertThat(paciente.getDataDeInicio()).isEqualTo(dataDeInicioEsperado);
        Assertions.assertThat(paciente.getQuantidaDeDiasNoMes()).isEqualTo(quantidaDeDiasNoMesEsperado);
        Assertions.assertThat(paciente.getValorPorSessao()).isEqualTo(valorPorSessaoEsperado);
        Assertions.assertThat(paciente.getInativo()).isFalse();
        Assertions.assertThat(paciente.getTipo()).isEqualTo(tipoDePacienteEsperado);
    }

    @ParameterizedTest
    @MethodSource("dadosNecessariosParaValidarCriacao")
    void naoDeveSerPossivelCriarUmPacienteSemOsDadosNecessarios(String nome,
                                                                String endereco,
                                                                Quantidade quantidaDeDiasNoMes,
                                                                Moeda valorPorSessao,
                                                                Tipo tipo,
                                                                String mensagemEsperada) {

        Throwable excecaoLancada = Assertions.catchThrowable(() -> new Paciente(nome, endereco, quantidaDeDiasNoMes, valorPorSessao, tipo));

        Assertions.assertThat(excecaoLancada).hasMessageContaining(mensagemEsperada);
    }

    @Test
    void deveSerPossivelAlterarOsDadosDeUmPaciente() {
        Paciente paciente = new PacienteBuilder().criar();
        String novoEnderecoEsperado = "Novo endereço.";
        Moeda novoValorPorSessaoEsperado = Moeda.criar(300);

        paciente.alterar(novoEnderecoEsperado, novoValorPorSessaoEsperado);

        Assertions.assertThat(paciente.getEndereco()).isEqualTo(novoEnderecoEsperado);
        Assertions.assertThat(paciente.getValorPorSessao()).isEqualTo(novoValorPorSessaoEsperado);
    }

    @ParameterizedTest
    @MethodSource("dadosNecessariosParaValidarAlteracao")
    void naoDeveSerPossivelAlterarUmPacienteSemOsDadosNecessarios(String endereco,
                                                                  Moeda valorPorSessao,
                                                                  String mensagemEsperada) {
        Paciente paciente = new PacienteBuilder().criar();

        Throwable excecaoLancada = Assertions.catchThrowable(() -> paciente.alterar(endereco, valorPorSessao));

        Assertions.assertThat(excecaoLancada).hasMessageContaining(mensagemEsperada);
    }

    @Test
    void deveSerPossivelInativarUmPaciente() {
        Paciente pacienteAtivo = new PacienteBuilder().ativo().criar();

        pacienteAtivo.inativar();

        Assertions.assertThat(pacienteAtivo.getInativo()).isTrue();
    }

    @Test
    void naoDeveSerPossivelInativarUmPacienteQueJahEstaInativo() {
        String mensagemEsperda = "O paciente já está inativo.";
        Paciente pacienteInativo = new PacienteBuilder().inativo().criar();

        Throwable excecaoLancada = Assertions.catchThrowable(pacienteInativo::inativar);

        Assertions.assertThat(excecaoLancada).hasMessageContaining(mensagemEsperda);
    }

    @Test
    void deveSerPossivelAtivarUmPaciente() {
        Paciente pacienteInativo = new PacienteBuilder().inativo().criar();

        pacienteInativo.ativar();

        Assertions.assertThat(pacienteInativo.getInativo()).isFalse();
    }

    @Test
    void naoDeveSerPossivelAtivarUmPacienteQueJahEstaAtivo() {
        String mensagemEsperda = "O paciente já está ativo.";
        Paciente pacienteAtivo = new PacienteBuilder().ativo().criar();

        Throwable excecaoLancada = Assertions.catchThrowable(pacienteAtivo::ativar);

        Assertions.assertThat(excecaoLancada).hasMessageContaining(mensagemEsperda);
    }

    private static Stream<Arguments> dadosNecessariosParaValidarCriacao() {
        String nome = "Teste";
        String endereco = "Endereço teste";
        Quantidade quantidaDeDiasNoMes = Quantidade.ZERO;
        Moeda valorPorSessao = Moeda.ZERO;
        Tipo tipo = Tipo.VALOR_FIXO;

        return Stream.of(
                Arguments.of(null, endereco, quantidaDeDiasNoMes, valorPorSessao, tipo,"Não é possível criar um paciente sem informar o nome."),
                Arguments.of(nome, null, quantidaDeDiasNoMes, valorPorSessao, tipo,"Não é possível criar um paciente sem informar o endereço."),
                Arguments.of(nome, endereco, null, valorPorSessao, tipo, "Não é possível criar um paciente sem quantidade de dias no mes."),
                Arguments.of(nome, endereco, quantidaDeDiasNoMes, null, tipo, "Não é possível criar um paciente sem informar o valor por sessão."),
                Arguments.of(nome, endereco, quantidaDeDiasNoMes, valorPorSessao, null, "Não é possível criar um paciente sem informar o tipo do paciente.")
        );
    }

    private static Stream<Arguments> dadosNecessariosParaValidarAlteracao() {
        String endereco = "Endereço teste";
        Moeda valorPorSessao = Moeda.ZERO;

        return Stream.of(
                Arguments.of(null, valorPorSessao, "Não é possível alterar um paciente sem informar o endereço."),
                Arguments.of(endereco, null, "Não é possível alterar um paciente sem informar o valor por sessão.")
        );
    }
}