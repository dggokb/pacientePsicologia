package br.com.diego.pscicologia.dominio.paciente;

import br.com.diego.pscicologia.builder.PacienteBuilder;
import br.com.diego.pscicologia.builder.ValorBuilder;
import br.com.diego.pscicologia.comum.Mes;
import br.com.diego.pscicologia.comum.Moeda;
import br.com.diego.pscicologia.comum.Quantidade;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

class PacienteFabricaTest {

    private PacienteFabrica fabrica;
    private String nome;
    private String endereco;
    private Quantidade quantidadeDeDiasNoMes;
    private Moeda valorPorSessao;
    private PacienteRepositorio pacienteRepositorio;
    private Mes mes;
    private Integer ano;
    private Tipo tipo;

    @BeforeEach
    void setUp() {
        nome = "Diego Guedes";
        endereco = "Rua Batatinha, Bairro das batatas";
        quantidadeDeDiasNoMes = Quantidade.criar(10);
        valorPorSessao = Moeda.criar(100);
        mes = Mes.ABRIL;
        ano = 2023;
        tipo = Tipo.VALOR_POR_SESSAO;
        pacienteRepositorio = Mockito.mock(PacienteRepositorio.class);
        fabrica = new PacienteFabrica(pacienteRepositorio);
    }

    @Test
    void deveSerPossivelFabricarUmPacienteQueNaoFoiCadastradoAinda() {
        String nome = "Diego";
        String endereco = "Av lalal lulu";
        Quantidade quantidadeDeDiasNoMes = Quantidade.criar(10);
        Moeda valorPorSessao = Moeda.criar(100);
        Mes mes = Mes.ABRIL;
        Integer ano = 2023;
        Tipo tipo = Tipo.VALOR_POR_SESSAO;
        Mockito.when(pacienteRepositorio.buscar(nome)).thenReturn(null);

        Paciente pacienteFabricado = fabrica.fabricar(nome, endereco, quantidadeDeDiasNoMes, valorPorSessao, mes, ano, tipo);

        Assertions.assertThat(pacienteFabricado.getNome()).isEqualTo(nome);
        Assertions.assertThat(pacienteFabricado.getEndereco()).isEqualTo(endereco);
        Assertions.assertThat(pacienteFabricado.getValores()).extracting(Valor::getValorPorSessao).containsOnly(valorPorSessao);
        Assertions.assertThat(pacienteFabricado.getValores()).extracting(Valor::getQuantidadeDeDiasNoMes).containsOnly(quantidadeDeDiasNoMes);
        Assertions.assertThat(pacienteFabricado.getValores()).extracting(Valor::getMes).containsOnly(mes);
        Assertions.assertThat(pacienteFabricado.getValores()).extracting(Valor::getAno).containsOnly(ano);
        Assertions.assertThat(pacienteFabricado.getTipo()).isEqualTo(tipo);
    }

    @Test
    void deveSerPossivelFabricarUmPacienteJahCadastradoQueNaoPossuaOMesmoMesEAno() {
        Mes mes = Mes.ABRIL;
        Integer ano = 2023;
        Valor valor = new ValorBuilder().comMes(Mes.JANEIRO).comAno(ano).criar();
        Paciente paciente = new PacienteBuilder().comValores(valor).criar();
        Mockito.when(pacienteRepositorio.buscar(nome)).thenReturn(paciente);

        Paciente pacienteFabricado = fabrica.fabricar(nome, endereco, quantidadeDeDiasNoMes, valorPorSessao, mes, ano, tipo);

        Assertions.assertThat(pacienteFabricado.getNome()).isEqualTo(nome);
        Assertions.assertThat(pacienteFabricado.getEndereco()).isEqualTo(endereco);
        Assertions.assertThat(pacienteFabricado.getValores()).extracting(Valor::getValorPorSessao).containsOnly(valorPorSessao, valor.getValorPorSessao());
        Assertions.assertThat(pacienteFabricado.getValores()).extracting(Valor::getQuantidadeDeDiasNoMes).containsOnly(quantidadeDeDiasNoMes, valor.getQuantidadeDeDiasNoMes());
        Assertions.assertThat(pacienteFabricado.getValores()).extracting(Valor::getMes).containsOnly(mes, valor.getMes());
        Assertions.assertThat(pacienteFabricado.getValores()).extracting(Valor::getAno).containsOnly(ano, valor.getAno());
        Assertions.assertThat(pacienteFabricado.getTipo()).isEqualTo(tipo);
    }

    @Test
    void naoDeveSerPossivelFabricarUmPacienteJahCadastradoSeJahPossuirOMesmoMesEAno() {
        Mes mes = Mes.ABRIL;
        Integer ano = 2023;
        String mensagemEsperada = String.format("Paciente já possui valores referente ao mês %s e ano %s.", mes, ano);
        Valor valor = new ValorBuilder().comMes(mes).comAno(ano).criar();
        Paciente paciente = new PacienteBuilder().comValores(valor).criar();
        Mockito.when(pacienteRepositorio.buscar(nome)).thenReturn(paciente);

        Throwable excecaoLancada = Assertions.catchThrowable(() -> fabrica.fabricar(nome, endereco, quantidadeDeDiasNoMes, valorPorSessao, mes, ano, tipo));

        Assertions.assertThat(excecaoLancada).hasMessageContaining(mensagemEsperada);
    }
}