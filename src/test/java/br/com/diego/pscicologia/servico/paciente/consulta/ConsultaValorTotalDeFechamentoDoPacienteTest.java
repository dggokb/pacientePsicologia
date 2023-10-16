package br.com.diego.pscicologia.servico.paciente.consulta;

import br.com.diego.pscicologia.comum.Mes;
import br.com.diego.pscicologia.comum.Moeda;
import br.com.diego.pscicologia.dominio.paciente.ServicoParaCalcularFechamentoDoPaciente;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

class ConsultaValorTotalDeFechamentoDoPacienteTest {

    private ServicoParaCalcularFechamentoDoPaciente servicoParaCalcularFechamentoDoPaciente;
    private ConsultaValorTotalDeFechamentoDoPaciente consulta;
    private String id;
    private Mes mes;
    private Integer ano;

    @BeforeEach
    void setUp() {
        servicoParaCalcularFechamentoDoPaciente = Mockito.mock(ServicoParaCalcularFechamentoDoPaciente.class);
        consulta = new ConsultaValorTotalDeFechamentoDoPacienteConcreto(servicoParaCalcularFechamentoDoPaciente);
        id = UUID.randomUUID().toString();
        mes = Mes.JANEIRO;
        ano = 2023;
    }

    @Test
    void deveSerPossivelObterOValorTotalDeFechamentoDoPaciente() throws Exception {
        String id = UUID.randomUUID().toString();
        Mes mes = Mes.JANEIRO;
        Integer ano = 2023;
        FiltroDeConsultaDeValorTotalDeFechamentoDoPaciente filtro = new FiltroDeConsultaDeValorTotalDeFechamentoDoPaciente(id, mes.name(), ano);
        Moeda valorEsperado = Moeda.criar(10);
        Mockito.when(servicoParaCalcularFechamentoDoPaciente.calcular(id, mes, ano)).thenReturn(valorEsperado);

        ValorTotalDeFechamentoDoPacienteDTO dto = consulta.consultar(filtro);

        Assertions.assertThat(dto.valorTotal).isEqualTo(valorEsperado.valor());
    }

    @Test
    void naoDeveSerPossivelObterOValorTotalDeFechamentoDoPacienteSeNaoConseguirCalcularOValorDoFechamento() throws Exception {
        String mensagemEsperada = "Não foi possível calcular o fechamendo do paciente.";
        FiltroDeConsultaDeValorTotalDeFechamentoDoPaciente filtro = new FiltroDeConsultaDeValorTotalDeFechamentoDoPaciente(id, mes.name(), ano);
        Mockito.when(servicoParaCalcularFechamentoDoPaciente.calcular(id, mes, ano)).thenReturn(null);

        Throwable excecaoLancada = Assertions.catchThrowable(() -> consulta.consultar(filtro));

        Assertions.assertThat(excecaoLancada).hasMessageContaining(mensagemEsperada);
    }
}