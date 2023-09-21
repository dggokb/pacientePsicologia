package br.com.diego.pscicologia.dominio.paciente;

import br.com.diego.pscicologia.comum.Mes;
import br.com.diego.pscicologia.comum.Moeda;
import br.com.diego.pscicologia.comum.Quantidade;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

class ValorTest {

    private Moeda valorPorSessao;
    private Mes mes;
    private Integer ano;
    private Quantidade quantidadeDeDiasNoMes;

    @BeforeEach
    void setUp() {
        quantidadeDeDiasNoMes = Quantidade.criar(10);
        valorPorSessao = Moeda.ZERO;
        mes = Mes.ABRIL;
        ano = LocalDate.now().getYear();
    }

    @Test
    void deveSerPossivelCriarUmValor() {
        Quantidade quantidadeDeDiasNoMesEsperada = Quantidade.criar(10);
        Moeda valorPorSessaoEsperado = Moeda.ZERO;
        Mes mesEsperado = Mes.ABRIL;
        Integer anoEsperado = LocalDate.now().getYear();

        Valor valor = new Valor(quantidadeDeDiasNoMesEsperada, valorPorSessaoEsperado, mesEsperado, anoEsperado);

        Assertions.assertThat(valor.getQuantidadeDeDiasNoMes()).isEqualTo(quantidadeDeDiasNoMesEsperada);
        Assertions.assertThat(valor.getValorPorSessao()).isEqualTo(valorPorSessaoEsperado);
        Assertions.assertThat(valor.getMes()).isEqualTo(mesEsperado);
        Assertions.assertThat(valor.getAno()).isEqualTo(anoEsperado);
    }

    @Test
    void deveSerPossivelCriarValorSemInformarAQuantidadeDeDiasDoMes() {
        Quantidade quantidadeDeDiasNoMes = null;

        Valor valor = new Valor(quantidadeDeDiasNoMes, valorPorSessao, mes, ano);

        Assertions.assertThat(valor.getQuantidadeDeDiasNoMes()).isNull();
        Assertions.assertThat(valor.getValorPorSessao()).isEqualTo(valorPorSessao);
        Assertions.assertThat(valor.getMes()).isEqualTo(mes);
        Assertions.assertThat(valor.getAno()).isEqualTo(ano);
    }

    @ParameterizedTest
    @MethodSource("dadosNecessariosParaValidarCriacao")
    void naoDeveSerPossivelCriarValorSemDadosNecessarios(Moeda valorPorSessao,
                                                         Mes mes,
                                                         Integer ano,
                                                         String mensagemEsperada) {

        Throwable excecaoLancada = Assertions.catchThrowable(() -> new Valor(quantidadeDeDiasNoMes, valorPorSessao, mes, ano));

        Assertions.assertThat(excecaoLancada).hasMessageContaining(mensagemEsperada);
    }

    private static Stream<Arguments> dadosNecessariosParaValidarCriacao() {
        Moeda valorPorSessao = Moeda.ZERO;
        Mes mes = Mes.ABRIL;
        Integer ano = LocalDate.now().getYear();

        return Stream.of(
                Arguments.of(null, mes, ano, "Não é possível criar um valor sem informar o valor por sessão."),
                Arguments.of(valorPorSessao, null, ano, "Não é possível criar um valor sem informar o mês."),
                Arguments.of(valorPorSessao, mes, null, "Não é possível criar um valor sem informar o ano.")
        );
    }
}