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
    void deveSerPossivelCriarUmPacienteDoTipoFixo() {
        String nomeEsperado = "Diego Guedes";
        String enderecoEsperado = "Av das Bananeiras";
        LocalDate dataDeInicioEsperado = LocalDate.now();
        Moeda valorPorSessaoEsperado = Moeda.criar(150);
        Tipo tipoDePacienteEsperado = Tipo.VALOR_FIXO;

        Paciente paciente = new Paciente(nomeEsperado, enderecoEsperado, valorPorSessaoEsperado);

        Assertions.assertThat(paciente.getNome()).isEqualTo(nomeEsperado);
        Assertions.assertThat(paciente.getEndereco()).isEqualTo(enderecoEsperado);
        Assertions.assertThat(paciente.getDataDeInicio()).isEqualTo(dataDeInicioEsperado);
        Assertions.assertThat(paciente.getValorPorSessao()).isEqualTo(valorPorSessaoEsperado);
        Assertions.assertThat(paciente.getInativo()).isFalse();
        Assertions.assertThat(paciente.getTipo()).isEqualTo(tipoDePacienteEsperado);
    }

    @Test
    void deveSerPossivelCriarUmPacienteDoTipoMensal() {
        String nomeEsperado = "Diego Guedes";
        String enderecoEsperado = "Av das Bananeiras";
        LocalDate dataDeInicioEsperado = LocalDate.now();
        Quantidade quantidadeDeDiasNoMesEsperado = Quantidade.criar(10);
        Moeda valorPorSessaoEsperado = Moeda.criar(150);
        Tipo tipoDePacienteEsperado = Tipo.VALOR_POR_SESSAO;

        Paciente paciente = new Paciente(nomeEsperado, enderecoEsperado, quantidadeDeDiasNoMesEsperado, valorPorSessaoEsperado);

        Assertions.assertThat(paciente.getNome()).isEqualTo(nomeEsperado);
        Assertions.assertThat(paciente.getEndereco()).isEqualTo(enderecoEsperado);
        Assertions.assertThat(paciente.getDataDeInicio()).isEqualTo(dataDeInicioEsperado);
        Assertions.assertThat(paciente.getQuantidadeDeDiasNoMes()).isEqualTo(quantidadeDeDiasNoMesEsperado);
        Assertions.assertThat(paciente.getValorPorSessao()).isEqualTo(valorPorSessaoEsperado);
        Assertions.assertThat(paciente.getInativo()).isFalse();
        Assertions.assertThat(paciente.getTipo()).isEqualTo(tipoDePacienteEsperado);
    }

    @ParameterizedTest
    @MethodSource("dadosNecessariosParaValidarCriacaoDeTipoMensal")
    void naoDeveSerPossivelCriarUmPacienteDeValorMensalSemOsDadosNecessarios(String nome,
                                                                             String endereco,
                                                                             Quantidade quantidaDeDiasNoMes,
                                                                             Moeda valorPorSessao,
                                                                             String mensagemEsperada) {

        Throwable excecaoLancada = Assertions.catchThrowable(() -> new Paciente(nome, endereco, quantidaDeDiasNoMes, valorPorSessao));

        Assertions.assertThat(excecaoLancada).hasMessageContaining(mensagemEsperada);
    }

    @ParameterizedTest
    @MethodSource("dadosNecessariosParaValidarCriacaoDeTipoFixo")
    void naoDeveSerPossivelCriarUmPacienteDeValorFixoSemOsDadosNecessarios(String nome,
                                                                           String endereco,
                                                                           Moeda valorPorSessao,
                                                                           String mensagemEsperada) {

        Throwable excecaoLancada = Assertions.catchThrowable(() -> new Paciente(nome, endereco, valorPorSessao));

        Assertions.assertThat(excecaoLancada).hasMessageContaining(mensagemEsperada);
    }

    @Test
    void deveSerPossivelAlterarOsDadosDeUmPaciente() {
        Paciente paciente = new PacienteBuilder().criarTipoValorPorSessao();
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
        Paciente paciente = new PacienteBuilder().criarTipoValorPorSessao();

        Throwable excecaoLancada = Assertions.catchThrowable(() -> paciente.alterar(endereco, valorPorSessao));

        Assertions.assertThat(excecaoLancada).hasMessageContaining(mensagemEsperada);
    }

    @Test
    void deveSerPossivelInativarUmPaciente() {
        Paciente pacienteAtivo = new PacienteBuilder().ativo().criarTipoValorPorSessao();

        pacienteAtivo.inativar();

        Assertions.assertThat(pacienteAtivo.getInativo()).isTrue();
    }

    @Test
    void naoDeveSerPossivelInativarUmPacienteQueJahEstaInativo() {
        String mensagemEsperda = "O paciente já está inativo.";
        Paciente pacienteInativo = new PacienteBuilder().inativo().criarTipoValorPorSessao();

        Throwable excecaoLancada = Assertions.catchThrowable(pacienteInativo::inativar);

        Assertions.assertThat(excecaoLancada).hasMessageContaining(mensagemEsperda);
    }

    @Test
    void deveSerPossivelAtivarUmPaciente() {
        Paciente pacienteInativo = new PacienteBuilder().inativo().criarTipoValorPorSessao();

        pacienteInativo.ativar();

        Assertions.assertThat(pacienteInativo.getInativo()).isFalse();
    }

    @Test
    void naoDeveSerPossivelAtivarUmPacienteQueJahEstaAtivo() {
        String mensagemEsperda = "O paciente já está ativo.";
        Paciente pacienteAtivo = new PacienteBuilder().ativo().criarTipoValorPorSessao();

        Throwable excecaoLancada = Assertions.catchThrowable(pacienteAtivo::ativar);

        Assertions.assertThat(excecaoLancada).hasMessageContaining(mensagemEsperda);
    }

    @Test
    void deveSerPossivelObterADescricaoDoTipoDoPaciente() {
        Tipo tipo = Tipo.VALOR_POR_SESSAO;
        Paciente paciente = new PacienteBuilder().comTipo(tipo).criarTipoValorPorSessao();

        String descricaoDoTipo = paciente.obterDescricaoDoTipo();

        Assertions.assertThat(descricaoDoTipo).isEqualTo(tipo.getDescricao());
    }

    private static Stream<Arguments> dadosNecessariosParaValidarCriacaoDeTipoMensal() {
        String nome = "Teste";
        String endereco = "Endereço teste";
        Quantidade quantidaDeDiasNoMes = Quantidade.ZERO;
        Moeda valorPorSessao = Moeda.ZERO;

        return Stream.of(
                Arguments.of(null, endereco, quantidaDeDiasNoMes, valorPorSessao, "Não é possível criar um paciente sem informar o nome."),
                Arguments.of(nome, null, quantidaDeDiasNoMes, valorPorSessao, "Não é possível criar um paciente sem informar o endereço."),
                Arguments.of(nome, endereco, null, valorPorSessao, "Não é possível criar um paciente sem quantidade de dias no mes."),
                Arguments.of(nome, endereco, quantidaDeDiasNoMes, null, "Não é possível criar um paciente sem informar o valor por sessão.")
        );
    }

    private static Stream<Arguments> dadosNecessariosParaValidarCriacaoDeTipoFixo() {
        String nome = "Teste";
        String endereco = "Endereço teste";
        Moeda valorPorSessao = Moeda.ZERO;

        return Stream.of(
                Arguments.of(null, endereco, valorPorSessao, "Não é possível criar um paciente sem informar o nome."),
                Arguments.of(nome, null, valorPorSessao, "Não é possível criar um paciente sem informar o endereço."),
                Arguments.of(nome, endereco, null, "Não é possível criar um paciente sem informar o valor por sessão.")
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