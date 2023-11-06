package br.com.diego.pscicologia.dominio.paciente;

import br.com.diego.pscicologia.builder.PacienteBuilder;
import br.com.diego.pscicologia.builder.ValorBuilder;
import br.com.diego.pscicologia.comum.Mes;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class PacienteTest {

    @Test
    void deveSerPossivelCriarUmPaciente() {
        String usuarioId = UUID.randomUUID().toString();
        String nomeEsperado = "Diego Guedes";
        String enderecoEsperado = "Av das Bananeiras";
        LocalDate dataDeInicioEsperado = LocalDate.now();
        List<Valor> valoresEsperados = Collections.singletonList(new ValorBuilder().criar());
        TipoDePaciente tipoDePacienteDePacienteEsperado = TipoDePaciente.VALOR_POR_SESSAO;

        Paciente paciente = new Paciente(usuarioId, nomeEsperado, enderecoEsperado, valoresEsperados, tipoDePacienteDePacienteEsperado);

        Assertions.assertThat(paciente.getUsuarioId()).isEqualTo(usuarioId);
        Assertions.assertThat(paciente.getNome()).isEqualTo(nomeEsperado);
        Assertions.assertThat(paciente.getEndereco()).isEqualTo(enderecoEsperado);
        Assertions.assertThat(paciente.getDataDeInicio()).isEqualTo(dataDeInicioEsperado);
        Assertions.assertThat(paciente.getValores()).isEqualTo(valoresEsperados);
        Assertions.assertThat(paciente.getTipo()).isEqualTo(tipoDePacienteDePacienteEsperado);
        Assertions.assertThat(paciente.getInativo()).isFalse();
    }

    @ParameterizedTest
    @MethodSource("dadosNecessariosParaValidarCriacao")
    void naoDeveSerPossivelCriarUmPacienteDeValorMensalSemOsDadosNecessarios(String usuarioId,
                                                                             String nome,
                                                                             String endereco,
                                                                             List<Valor> valores,
                                                                             TipoDePaciente tipoDePaciente,
                                                                             String mensagemEsperada) {

        Throwable excecaoLancada = Assertions.catchThrowable(() -> new Paciente(usuarioId, nome, endereco, valores, tipoDePaciente));

        Assertions.assertThat(excecaoLancada).hasMessageContaining(mensagemEsperada);
    }

    @Test
    void deveSerPossivelAlterarOsDadosDeUmPaciente() {
        Valor valor = new ValorBuilder().criar();
        Paciente paciente = new PacienteBuilder().comValores(valor).criar();
        String novoEnderecoEsperado = "Novo endereço.";

        paciente.alterar(paciente.getNome(),
                novoEnderecoEsperado,
                paciente.getTipo(),
                paciente.getValores());

        Assertions.assertThat(paciente.getEndereco()).isEqualTo(novoEnderecoEsperado);
    }

    @ParameterizedTest
    @MethodSource("dadosNecessariosParaValidarAlteracao")
    void naoDeveSerPossivelAlterarUmPacienteSemOsDadosNecessarios(String nome,
                                                                  String endereco,
                                                                  TipoDePaciente tipoDePaciente,
                                                                  List<Valor> valores,
                                                                  String mensagemEsperada) {
        Paciente paciente = new PacienteBuilder().criar();

        Throwable excecaoLancada = Assertions.catchThrowable(() -> paciente.alterar(nome, endereco, tipoDePaciente, valores));

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

    @Test
    void deveSerPossivelObterADescricaoDoTipoDoPaciente() {
        TipoDePaciente tipoDePaciente = TipoDePaciente.VALOR_POR_SESSAO;
        Paciente paciente = new PacienteBuilder().comTipo(tipoDePaciente).criar();

        String descricaoDoTipo = paciente.obterDescricaoDoTipo();

        Assertions.assertThat(descricaoDoTipo).isEqualTo(tipoDePaciente.getDescricao());
    }

    @Test
    void deveSerPossivelAlterarOsValoresDeUmPaciente() {
        Paciente paciente = new PacienteBuilder().criar();
        Valor valorEsperado = new ValorBuilder().comMes(Mes.ABRIL).comAno(2011).criar();

        paciente.alterar(Collections.singletonList(valorEsperado));

        Assertions.assertThat(paciente.getValores()).containsOnly(valorEsperado);
    }

    @ParameterizedTest
    @EmptySource
    void naoDeveSerPossivelAlterarOsValoresDeUmPacienteSeNaoInformarOsValores(List<Valor> valores) {
        String mensagemEsperada = "Não é possível adicionar um valor ao paciente, pois, não foi informado os valores.";
        Paciente paciente = new PacienteBuilder().criar();

        Throwable excecaoLancada = Assertions.catchThrowable(() -> paciente.alterar(valores));

        Assertions.assertThat(excecaoLancada).hasMessageContaining(mensagemEsperada);
    }

    private static Stream<Arguments> dadosNecessariosParaValidarCriacao() {
        String usuarioId = UUID.randomUUID().toString();
        String nome = "Teste";
        String endereco = "Endereço teste";
        List<Valor> valores = Collections.singletonList(new ValorBuilder().criar());
        TipoDePaciente tipoDePaciente = TipoDePaciente.VALOR_POR_SESSAO;

        return Stream.of(
                Arguments.of(null, nome, endereco, valores, tipoDePaciente, "Não é possível criar um paciente sem informar o usuário."),
                Arguments.of(usuarioId, null, endereco, valores, tipoDePaciente, "Não é possível criar um paciente sem informar o nome."),
                Arguments.of(usuarioId, nome, null, valores, tipoDePaciente, "Não é possível criar um paciente sem informar o endereço."),
                Arguments.of(usuarioId, nome, endereco, Collections.emptyList(), tipoDePaciente, "Não é possível criar um paciente sem informar o valor."),
                Arguments.of(usuarioId, nome, endereco, valores, null, "Não é possível criar um paciente sem informar o tipo.")
        );
    }

    private static Stream<Arguments> dadosNecessariosParaValidarAlteracao() {
        String nome = "Teste";
        String endereco = "AV. Teste";
        TipoDePaciente tipo = TipoDePaciente.VALOR_POR_SESSAO;
        List<Valor> valores = Collections.singletonList(new ValorBuilder().criar());
        return Stream.of(
                Arguments.of(null, endereco, tipo, valores, "Não é possível alterar um paciente sem informar o nome."),
                Arguments.of(nome, null, tipo, valores, "Não é possível alterar um paciente sem informar o endereço."),
                Arguments.of(nome, endereco, null, valores, "Não é possível alterar um paciente sem informar o tipo."),
                Arguments.of(nome, endereco, tipo, Collections.emptyList(), "Não é possível alterar um paciente sem informar os valores das sessões.")
        );
    }
}