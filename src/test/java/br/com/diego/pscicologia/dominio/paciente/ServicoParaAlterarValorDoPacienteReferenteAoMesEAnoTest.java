package br.com.diego.pscicologia.dominio.paciente;

import br.com.diego.pscicologia.builder.ValorBuilder;
import br.com.diego.pscicologia.comum.Mes;
import br.com.diego.pscicologia.comum.Moeda;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

class ServicoParaAlterarValorDoPacienteReferenteAoMesEAnoTest {

    private ServicoParaAlterarValorDoPacienteReferenteAoMesEAno servico;
    private Mes janeiro;
    private Mes agosto;
    private Integer ano;
    private TipoDePaciente tipo;
    private Mes fevereiro;

    @BeforeEach
    void setUp() {
        servico = new ServicoParaAlterarValorDoPacienteReferenteAoMesEAno();
        janeiro = Mes.JANEIRO;
        agosto = Mes.AGOSTO;
        ano = LocalDate.now().getYear();
        tipo = TipoDePaciente.VALOR_POR_SESSAO;
        fevereiro = Mes.FEVEREIRO;
    }

    @Test
    void deveSerPossivelCriarValoresDoMesmoMesEAno() {
        Mes janeiro = Mes.JANEIRO;
        Mes agosto = Mes.AGOSTO;
        Integer ano = LocalDate.now().getYear();
        Valor valorAntigo = new ValorBuilder().comValorPorSessao(Moeda.criar(10)).comMes(janeiro).comAno(ano).criar();
        Valor valorAntigoDeOutroMes = new ValorBuilder().comValorPorSessao(Moeda.criar(20)).comMes(agosto).comAno(ano).criar();
        Valor valorAntigoDeOutroAno = new ValorBuilder().comValorPorSessao(Moeda.criar(10)).comMes(janeiro).comAno(ano + 2).criar();
        List<Valor> valoresAtuais = Arrays.asList(valorAntigo, valorAntigoDeOutroMes, valorAntigoDeOutroAno);
        Moeda novoValorPorSessao = Moeda.criar(350);
        TipoDePaciente tipo = TipoDePaciente.VALOR_POR_SESSAO;
        List<LocalDate> novasDatasDasSessoes = Arrays.asList(LocalDate.now(), LocalDate.now().plusDays(1));

        List<Valor> novosValores = servico.alterar(valoresAtuais, novoValorPorSessao, janeiro, ano, tipo, novasDatasDasSessoes);

        Assertions.assertThat(novosValores).extracting(Valor::getValorPorSessao).containsOnly(novoValorPorSessao,
                valorAntigoDeOutroMes.getValorPorSessao(), valorAntigoDeOutroAno.getValorPorSessao());
        Assertions.assertThat(novosValores).extracting(Valor::getMes).containsOnly(janeiro, agosto, janeiro);
        Assertions.assertThat(novosValores).extracting(Valor::getAno).containsOnly(valorAntigo.getAno(),
                valorAntigoDeOutroMes.getAno(), valorAntigoDeOutroAno.getAno());
        Assertions.assertThat(novosValores).extracting(Valor::getDatasDasSessoes).containsOnly(novasDatasDasSessoes,
                valorAntigoDeOutroMes.getDatasDasSessoes(), valorAntigoDeOutroAno.getDatasDasSessoes());
    }

    @Test
    void naoDeveAlterarNenhumValorSeNaoPossuirValoresComMesmoMesEAno() {
        Valor valorAntigoDeOutroMes = new ValorBuilder().comMes(agosto).comAno(ano + 1).criar();
        Valor valorAntigoDeOutroAno = new ValorBuilder().comMes(janeiro).comAno(ano + 2).criar();
        Valor valorAntigoDeOutroMesEAno = new ValorBuilder().comMes(fevereiro).comAno(ano + 3).criar();
        List<Valor> valoresAtuais = Arrays.asList(valorAntigoDeOutroMesEAno, valorAntigoDeOutroMes, valorAntigoDeOutroAno);
        Moeda novoValorPorSessao = Moeda.criar(999);
        List<LocalDate> novasDatasDasSessoes = Arrays.asList(LocalDate.now(), LocalDate.now().plusDays(1));

        List<Valor> novosValores = servico.alterar(valoresAtuais, novoValorPorSessao, janeiro, ano, tipo, novasDatasDasSessoes);

        Assertions.assertThat(novosValores).extracting(Valor::getValorPorSessao).containsOnly(valorAntigoDeOutroMes.getValorPorSessao(),
                valorAntigoDeOutroAno.getValorPorSessao(), valorAntigoDeOutroMesEAno.getValorPorSessao());
        Assertions.assertThat(novosValores).extracting(Valor::getMes).containsOnly(valorAntigoDeOutroMes.getMes(), valorAntigoDeOutroAno.getMes(),
                valorAntigoDeOutroMesEAno.getMes());
        Assertions.assertThat(novosValores).extracting(Valor::getAno).containsOnly(valorAntigoDeOutroMes.getAno(),
                valorAntigoDeOutroAno.getAno(), valorAntigoDeOutroMesEAno.getAno());
        Assertions.assertThat(novosValores).extracting(Valor::getDatasDasSessoes).containsOnly(valorAntigoDeOutroMes.getDatasDasSessoes(),
                valorAntigoDeOutroAno.getDatasDasSessoes(), valorAntigoDeOutroMesEAno.getDatasDasSessoes());
    }

    @ParameterizedTest
    @MethodSource("dadosNecessariosParaValidarAlteracao")
    void naoDeveSerPossivelAlterarOValorDoPacienteReferenteAoMesEAoAnoSemAsInformacoesNecessarias(List<Valor> valoresAtuais,
                                                                                                  Moeda novoValorPorSessao,
                                                                                                  Mes mes,
                                                                                                  Integer ano,
                                                                                                  TipoDePaciente tipoDePaciente,
                                                                                                  String mensagemEsperada) {
        List<LocalDate> novasDatasDasSessoes = Arrays.asList(LocalDate.now(), LocalDate.now().plusDays(1));

        Throwable excecaoLancada = Assertions.catchThrowable(() -> servico.alterar(valoresAtuais, novoValorPorSessao, mes, ano, tipoDePaciente, novasDatasDasSessoes));

        Assertions.assertThat(excecaoLancada).hasMessageContaining(mensagemEsperada);
    }

    private static Stream<Arguments> dadosNecessariosParaValidarAlteracao() {
        List<Valor> valoresAtuais = Collections.singletonList(new ValorBuilder().criar());
        Moeda novoValorPorSessao = Moeda.criar(50);
        Mes mes = Mes.ABRIL;
        Integer ano = LocalDate.now().getYear();
        TipoDePaciente tipoDePaciente = TipoDePaciente.VALOR_FIXO;

        return Stream.of(
                Arguments.of(Collections.emptyList(), novoValorPorSessao, mes, ano, tipoDePaciente, "Não é possível alterar um valor sem informar os valores atuais do mês e ano."),
                Arguments.of(valoresAtuais, null, mes, ano, tipoDePaciente, "Não é possível alterar um valor sem informar o novo valor por sessão."),
                Arguments.of(valoresAtuais, novoValorPorSessao, null, ano, tipoDePaciente, "Não é possível alterar um valor sem informar o mês."),
                Arguments.of(valoresAtuais, novoValorPorSessao, mes, null, tipoDePaciente, "Não é possível alterar um valor sem informar o ano."),
                Arguments.of(valoresAtuais, novoValorPorSessao, mes, ano, null, "Não é possível alterar um valor sem informar o tipo.")
                );
    }
}