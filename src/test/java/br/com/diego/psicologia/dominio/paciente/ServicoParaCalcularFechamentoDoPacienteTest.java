package br.com.diego.psicologia.dominio.paciente;

import br.com.diego.psicologia.builder.PacienteBuilder;
import br.com.diego.psicologia.builder.ValorBuilder;
import br.com.diego.psicologia.comum.Mes;
import br.com.diego.psicologia.comum.Moeda;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

class ServicoParaCalcularFechamentoDoPacienteTest {

    private ServicoParaCalcularFechamentoDoPaciente servico;
    private PacienteRepositorio pacienteRepositorio;
    private Integer ano;
    private Mes mes;
    private String id;

    @BeforeEach
    void setUp() {
        pacienteRepositorio = Mockito.mock(PacienteRepositorio.class);
        servico = new ServicoParaCalcularFechamentoDoPaciente(pacienteRepositorio);
        ano = 2023;
        mes = Mes.JANEIRO;
        id = UUID.randomUUID().toString();
    }

    @Test
    void deveSerPossivelCalcularOValorCalculadoDoFechamentoDoPaciente() {
        String id = UUID.randomUUID().toString();
        Mes mes = Mes.JANEIRO;
        Integer ano = 2023;
        Valor valor = new ValorBuilder().comMes(mes).comAno(ano).criar();
        Paciente paciente = new PacienteBuilder().comValores(valor).criar();
        Mockito.when(pacienteRepositorio.findById(id)).thenReturn(Optional.ofNullable(paciente));

        Moeda valorCalculado = servico.calcular(id, mes, ano);

        Assertions.assertThat(valorCalculado).isEqualTo(valor.obterValorTotal());
    }

    @Test
    void naoDeveSerPossivelCalcularOValorDoFechamentoDoPacienteSeNaoEncontrarOPaciente() {
        String mensagemEsperada = "Não foi possível encontar o paciente para obter o valor do fechamento.";
        Mockito.when(pacienteRepositorio.findById(id)).thenReturn(Optional.empty());

        Throwable excecaoLancada = Assertions.catchThrowable(() -> servico.calcular(id, mes, ano));

        Assertions.assertThat(excecaoLancada).hasMessageContaining(mensagemEsperada);
    }

    @Test
    void naoDeveSerPossivelCalcularOValorDoFechamentoDoPacienteSeNaoPossuirValorNoMes() {
        Mes outroMesInformado = Mes.AGOSTO;
        String mensagemEsperada = String.format("Não foi possível encontrar o valor do mês %s do ano %s", outroMesInformado.getDescricao(), ano);
        Valor valor = new ValorBuilder().comMes(mes).comAno(ano).criar();
        Paciente paciente = new PacienteBuilder().comValores(valor).criar();
        Mockito.when(pacienteRepositorio.findById(id)).thenReturn(Optional.ofNullable(paciente));

        Throwable excecaoLancada = Assertions.catchThrowable(() -> servico.calcular(id, outroMesInformado, ano));

        Assertions.assertThat(excecaoLancada).hasMessageContaining(mensagemEsperada);
    }

    @Test
    void naoDeveSerPossivelCalcularOValorDoFechamentoDoPacienteSeNaoPossuirValorNoAno() {
        Integer outroAnoInformado = 2022;
        String mensagemEsperada = String.format("Não foi possível encontrar o valor do mês %s do ano %s", mes.getDescricao(), outroAnoInformado);
        Valor valor = new ValorBuilder().comMes(mes).comAno(ano).criar();
        Paciente paciente = new PacienteBuilder().comValores(valor).criar();
        Mockito.when(pacienteRepositorio.findById(id)).thenReturn(Optional.ofNullable(paciente));

        Throwable excecaoLancada = Assertions.catchThrowable(() -> servico.calcular(id, mes, outroAnoInformado));

        Assertions.assertThat(excecaoLancada).hasMessageContaining(mensagemEsperada);
    }

    @Test
    void naoDeveSerPossivelCalcularOValorDoFechamentoDoPacienteSeNaoPossuirValorNoAnoENoMes() {
        Mes outroMesInformado = Mes.AGOSTO;
        Integer outroAnoInformado = 2022;
        String mensagemEsperada = String.format("Não foi possível encontrar o valor do mês %s do ano %s", outroMesInformado.getDescricao(), outroAnoInformado);
        Valor valor = new ValorBuilder().comMes(mes).comAno(ano).criar();
        Paciente paciente = new PacienteBuilder().comValores(valor).criar();
        Mockito.when(pacienteRepositorio.findById(id)).thenReturn(Optional.ofNullable(paciente));

        Throwable excecaoLancada = Assertions.catchThrowable(() -> servico.calcular(id, outroMesInformado, outroAnoInformado));

        Assertions.assertThat(excecaoLancada).hasMessageContaining(mensagemEsperada);
    }

    @ParameterizedTest
    @MethodSource("dadosNecessariosParaValidarCalculo")
    void naoDeveSerPossivelCalcularOValorDoFechamentoSemDadosNecessarios(String id,
                                                                         Mes mes,
                                                                         Integer ano,
                                                                         String mensagemEsperada) {
        Throwable excecaoLancada = Assertions.catchThrowable(() -> servico.calcular(id, mes, ano));

        Assertions.assertThat(excecaoLancada).hasMessageContaining(mensagemEsperada);
    }

    private static Stream<Arguments> dadosNecessariosParaValidarCalculo() {
        String id = UUID.randomUUID().toString();
        Mes mes = Mes.ABRIL;
        Integer ano = LocalDate.now().getYear();

        return Stream.of(
                Arguments.of(null, mes, ano, "É necessário informar o paciente para obter o valor do fechamento."),
                Arguments.of(id, null, ano, "É necessário informar o mês para obter o valor do fechamento."),
                Arguments.of(id, mes, null, "É necessário informar o ano para obter o valor do fechamento.")
        );
    }
}