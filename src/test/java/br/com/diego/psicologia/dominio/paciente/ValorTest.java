package br.com.diego.psicologia.dominio.paciente;

import br.com.diego.psicologia.builder.ValorBuilder;
import br.com.diego.psicologia.comum.Mes;
import br.com.diego.psicologia.comum.Moeda;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

class ValorTest {

    private Moeda valorPorSessao;
    private Mes mes;
    private Integer ano;
    private List<LocalDate> datasDasSessoes;

    @BeforeEach
    void setUp() {
        valorPorSessao = Moeda.ZERO;
        mes = Mes.ABRIL;
        ano = LocalDate.now().getYear();
        datasDasSessoes = Arrays.asList(LocalDate.now(), LocalDate.now().plusDays(1));
    }

    @Test
    void deveSerPossivelCriarUmValorComQuantidadeDeDiasNoMesSeOTipoForValorPorSessao() {
        Moeda valorPorSessaoEsperado = Moeda.ZERO;
        Mes mesEsperado = Mes.ABRIL;
        Integer anoEsperado = LocalDate.now().getYear();
        TipoDePaciente tipoDePaciente = TipoDePaciente.VALOR_POR_SESSAO;
        List<LocalDate> datasDasSessoes = Arrays.asList(LocalDate.now(), LocalDate.now().plusDays(1));

        Valor valor = new Valor(valorPorSessaoEsperado, mesEsperado, anoEsperado, tipoDePaciente, datasDasSessoes);

        Assertions.assertThat(valor.getQuantidadeDeDiasNoMes().quantidade()).isEqualTo(datasDasSessoes.size());
        Assertions.assertThat(valor.getValorPorSessao()).isEqualTo(valorPorSessaoEsperado);
        Assertions.assertThat(valor.getMes()).isEqualTo(mesEsperado);
        Assertions.assertThat(valor.getAno()).isEqualTo(anoEsperado);
        Assertions.assertThat(valor.getDatasDasSessoes()).containsOnly(datasDasSessoes.get(0), datasDasSessoes.get(1));
    }

    @Test
    void deveSerPossivelCriarValorSemAQuantidadeDeDiasDoMesSeOTipoForValorFixo() {
        TipoDePaciente tipoDePaciente = TipoDePaciente.VALOR_FIXO;

        Valor valor = new Valor(valorPorSessao, mes, ano, tipoDePaciente, datasDasSessoes);

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
                                                         List<LocalDate> datasDasSessoes,
                                                         String mensagemEsperada) {

        Throwable excecaoLancada = Assertions.catchThrowable(() -> new Valor(valorPorSessao, mes, ano, tipoDePaciente, datasDasSessoes));

        Assertions.assertThat(excecaoLancada).hasMessageContaining(mensagemEsperada);
    }

    @Test
    void naoDeveSerPossivelCriarValorSemInformarAQuantidadeDeDiasDoMesSeOTipoForValorPorSessao() {
        String mensagemEsperada = "Não pode ser inserido um valor sem quantidade de dias no mês quando for valor por sessão.";
        List<LocalDate> datasDasSessoes = Collections.emptyList();
        TipoDePaciente tipoDePaciente = TipoDePaciente.VALOR_POR_SESSAO;

        Throwable excecaoLancada = Assertions.catchThrowable(() -> new Valor(valorPorSessao, mes, ano, tipoDePaciente, datasDasSessoes));

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
    void deveSerPossivelCriarUmValorSemDatasDasSessoesSeForDoTipoFixo() {
        List<LocalDate> datasDasSessoes = Collections.emptyList();
        TipoDePaciente tipoDePaciente = TipoDePaciente.VALOR_FIXO;

        Valor valor = new Valor(valorPorSessao, mes, ano, tipoDePaciente, datasDasSessoes);

        Assertions.assertThat(valor.getQuantidadeDeDiasNoMes()).isNull();
    }

    @Test
    void deveSerPossivelCriarUmValorSemDatasDasSessoesSeForDoTipoFixoMesmoSeForInformadoAsDatas() {
        List<LocalDate> datasDasSessoes = List.of(LocalDate.now());
        TipoDePaciente tipoDePaciente = TipoDePaciente.VALOR_FIXO;

        Valor valor = new Valor(valorPorSessao, mes, ano, tipoDePaciente, datasDasSessoes);

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
        List<LocalDate> datasDasSessoes = Arrays.asList(LocalDate.now(), LocalDate.now().plusDays(1));

        return Stream.of(
                Arguments.of(null, mes, ano, tipoDePaciente, datasDasSessoes, "Não é possível criar um valor sem informar o valor por sessão."),
                Arguments.of(valorPorSessao, null, ano, tipoDePaciente, datasDasSessoes, "Não é possível criar um valor sem informar o mês."),
                Arguments.of(valorPorSessao, mes, null, tipoDePaciente, datasDasSessoes, "Não é possível criar um valor sem informar o ano."),
                Arguments.of(valorPorSessao, mes, ano, null, datasDasSessoes, "Não é possível criar um valor sem informar o tipo.")
        );
    }
}