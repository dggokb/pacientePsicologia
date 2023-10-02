package br.com.diego.pscicologia.dominio.paciente;

import br.com.diego.pscicologia.builder.ValorBuilder;
import br.com.diego.pscicologia.comum.Mes;
import br.com.diego.pscicologia.comum.Moeda;
import br.com.diego.pscicologia.comum.Quantidade;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
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
    void deveSerPossivelCriarUmValorComQuantidadeDeDiasNoMesSeOTipoForValorPorSessao() {
        Quantidade quantidadeDeDiasNoMesEsperada = Quantidade.criar(10);
        Moeda valorPorSessaoEsperado = Moeda.ZERO;
        Mes mesEsperado = Mes.ABRIL;
        Integer anoEsperado = LocalDate.now().getYear();
        TipoDePaciente tipoDePaciente = TipoDePaciente.VALOR_POR_SESSAO;

        Valor valor = new Valor(quantidadeDeDiasNoMesEsperada, valorPorSessaoEsperado, mesEsperado, anoEsperado, tipoDePaciente);

        Assertions.assertThat(valor.getQuantidadeDeDiasNoMes()).isEqualTo(quantidadeDeDiasNoMesEsperada);
        Assertions.assertThat(valor.getValorPorSessao()).isEqualTo(valorPorSessaoEsperado);
        Assertions.assertThat(valor.getMes()).isEqualTo(mesEsperado);
        Assertions.assertThat(valor.getAno()).isEqualTo(anoEsperado);
    }

    @Test
    void deveSerPossivelCriarValorSemInformarAQuantidadeDeDiasDoMesSeOTipoForValorFixo() {
        Quantidade quantidadeDeDiasNoMes = null;
        TipoDePaciente tipoDePaciente = TipoDePaciente.VALOR_FIXO;

        Valor valor = new Valor(quantidadeDeDiasNoMes, valorPorSessao, mes, ano, tipoDePaciente);

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
                                                         TipoDePaciente tipoDePaciente,
                                                         String mensagemEsperada) {

        Throwable excecaoLancada = Assertions.catchThrowable(() -> new Valor(quantidadeDeDiasNoMes, valorPorSessao, mes, ano, tipoDePaciente));

        Assertions.assertThat(excecaoLancada).hasMessageContaining(mensagemEsperada);
    }

    @Test
    void naoDeveSerPossivelCriarValorSemInformarAQuantidadeDeDiasDoMesSeOTipoForValorPorSessao() {
        String mensagemEsperada = "Não pode ser inserido um valor sem quantidade de dias no mês quando for valor por sessão.";
        Quantidade quantidadeDeDiasNoMes = null;
        TipoDePaciente tipoDePaciente = TipoDePaciente.VALOR_POR_SESSAO;

        Throwable excecaoLancada = Assertions.catchThrowable(() -> new Valor(quantidadeDeDiasNoMes, valorPorSessao, mes, ano, tipoDePaciente));

        Assertions.assertThat(excecaoLancada).hasMessageContaining(mensagemEsperada);
    }

    @ParameterizedTest
    @CsvSource({"JANEIRO, 2023, TRUE",
            "FEVEREIRO, 2023, FALSE",
            "JANEIRO, 2022, FALSE",})
    void deveSerPossivelInformarSeEhDoMesmoMesEAno(String mesParaComparacao, Integer anoParaComparacao, Boolean retornoEsperado) {
        Mes mes = Mes.JANEIRO;
        int ano = 2023;
        Valor valor = new ValorBuilder().comMes(mes).comAno(ano).criar();

        boolean ehDoMesmoMesEAno = valor.ehDoMesmo(Mes.valueOf(mesParaComparacao), anoParaComparacao);

        Assertions.assertThat(ehDoMesmoMesEAno).isEqualTo(retornoEsperado);
    }

    @Test
    void deveSerPossivelCriarUmValorComQuantidadePorDiasNuloSeForDoTipoFixoMesmoInformandoQuantidadeDeDiasPorSessao() {
        Quantidade quantidadeDeDiasNoMes = Quantidade.criar(10);
        TipoDePaciente tipoDePaciente = TipoDePaciente.VALOR_FIXO;

        Valor valor = new Valor(quantidadeDeDiasNoMes, valorPorSessao, mes, ano, tipoDePaciente);

        Assertions.assertThat(valor.getQuantidadeDeDiasNoMes()).isNull();
    }

    @Test
    void deveSerPossivelObterOValorTotalDoFechamentoQuandoForDoTipoFixo() {
        Valor valor = new ValorBuilder().comTipo(TipoDePaciente.VALOR_FIXO).criar();

        Moeda valorTotal = valor.obterValorTotal();

        Assertions.assertThat(valorTotal).isEqualTo(valor.getValorPorSessao());
    }

    @Test
    void deveSerPossivelObterOValorTotalDoFechamentoQuandoForDoTipoValorPorSessao() {
        Valor valor = new ValorBuilder().comTipo(TipoDePaciente.VALOR_POR_SESSAO).criar();
        Moeda valorEsperado = valor.getValorPorSessao().multiplicar(valor.getQuantidadeDeDiasNoMes());

        Moeda valorTotal = valor.obterValorTotal();

        Assertions.assertThat(valorTotal).isEqualTo(valorEsperado);
    }

    private static Stream<Arguments> dadosNecessariosParaValidarCriacao() {
        Moeda valorPorSessao = Moeda.ZERO;
        Mes mes = Mes.ABRIL;
        Integer ano = LocalDate.now().getYear();
        TipoDePaciente tipoDePaciente = TipoDePaciente.VALOR_POR_SESSAO;

        return Stream.of(
                Arguments.of(null, mes, ano, tipoDePaciente, "Não é possível criar um valor sem informar o valor por sessão."),
                Arguments.of(valorPorSessao, null, ano, tipoDePaciente, "Não é possível criar um valor sem informar o mês."),
                Arguments.of(valorPorSessao, mes, null, tipoDePaciente, "Não é possível criar um valor sem informar o ano."),
                Arguments.of(valorPorSessao, mes, ano, null, "Não é possível criar um valor sem informar o tipo.")
        );
    }
}