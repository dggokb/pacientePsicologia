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

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

class PacienteFabricaTest {

    private PacienteFabrica fabrica;
    private String usuarioId;
    private String nome;
    private String endereco;
    private Quantidade quantidadeDeDiasNoMes;
    private Moeda valorPorSessao;
    private PacienteRepositorio pacienteRepositorio;
    private Mes mes;
    private Integer ano;
    private TipoDePaciente tipoDePaciente;
    private List<LocalDate> datasDasSessoes;

    @BeforeEach
    void setUp() {
        usuarioId = UUID.randomUUID().toString();
        nome = "Diego Guedes";
        endereco = "Rua Batatinha, Bairro das batatas";
        valorPorSessao = Moeda.criar(100);
        mes = Mes.ABRIL;
        ano = 2023;
        tipoDePaciente = TipoDePaciente.VALOR_FIXO;
        pacienteRepositorio = Mockito.mock(PacienteRepositorio.class);
        fabrica = new PacienteFabrica(pacienteRepositorio);
        datasDasSessoes = Collections.singletonList(LocalDate.now());
        quantidadeDeDiasNoMes = Quantidade.criar(datasDasSessoes.size());
    }

    @Test
    void deveSerPossivelFabricarUmPacienteQueAindaNaoFoiCadastrado() {
        String usuarioId = UUID.randomUUID().toString();
        String nome = "Diego";
        String endereco = "Av lalal lulu";
        Moeda valorPorSessao = Moeda.criar(100);
        Mes mes = Mes.ABRIL;
        Integer ano = 2023;
        TipoDePaciente tipoDePaciente = TipoDePaciente.VALOR_POR_SESSAO;
        List<LocalDate> datasDasSessoes = Collections.singletonList(LocalDate.now());
        Quantidade quantidadeDeDiasNoMes = Quantidade.criar(datasDasSessoes.size());
        Mockito.when(pacienteRepositorio.buscar(nome, usuarioId)).thenReturn(null);

        Paciente pacienteFabricado = fabrica.fabricar(usuarioId, nome, endereco, valorPorSessao, mes, ano, tipoDePaciente, datasDasSessoes);

        Assertions.assertThat(pacienteFabricado.getUsuarioId()).isEqualTo(usuarioId);
        Assertions.assertThat(pacienteFabricado.getNome()).isEqualTo(nome);
        Assertions.assertThat(pacienteFabricado.getEndereco()).isEqualTo(endereco);
        Assertions.assertThat(pacienteFabricado.getValores()).extracting(Valor::getValorPorSessao).containsOnly(valorPorSessao);
        Assertions.assertThat(pacienteFabricado.getValores()).extracting(Valor::getQuantidadeDeDiasNoMes).containsOnly(quantidadeDeDiasNoMes);
        Assertions.assertThat(pacienteFabricado.getValores()).extracting(Valor::getDatasDasSessoes).containsOnly(datasDasSessoes);
        Assertions.assertThat(pacienteFabricado.getValores()).extracting(Valor::getMes).containsOnly(mes);
        Assertions.assertThat(pacienteFabricado.getValores()).extracting(Valor::getAno).containsOnly(ano);
        Assertions.assertThat(pacienteFabricado.getTipo()).isEqualTo(tipoDePaciente);
    }

    @Test
    void deveSerPossivelFabricarUmPacienteJahCadastradoQueNaoPossuaOMesmoMesEAno() {
        Mes mes = Mes.ABRIL;
        Integer ano = 2023;
        Valor valor = new ValorBuilder().comMes(Mes.JANEIRO).comAno(ano).criar();
        Paciente paciente = new PacienteBuilder().comValores(valor).criar();
        Mockito.when(pacienteRepositorio.buscar(nome, paciente.getUsuarioId())).thenReturn(paciente);

        Paciente pacienteFabricado = fabrica.fabricar(paciente.getUsuarioId(), nome, endereco, valorPorSessao, mes, ano, tipoDePaciente, datasDasSessoes);

        Assertions.assertThat(pacienteFabricado.getUsuarioId()).isEqualTo(paciente.getUsuarioId());
        Assertions.assertThat(pacienteFabricado.getNome()).isEqualTo(nome);
        Assertions.assertThat(pacienteFabricado.getEndereco()).isEqualTo(endereco);
        Assertions.assertThat(pacienteFabricado.getValores()).extracting(Valor::getValorPorSessao).containsOnly(valorPorSessao, valor.getValorPorSessao());
        Assertions.assertThat(pacienteFabricado.getValores()).extracting(Valor::getQuantidadeDeDiasNoMes).containsOnly(null, valor.getQuantidadeDeDiasNoMes());
        Assertions.assertThat(pacienteFabricado.getValores()).extracting(Valor::getDatasDasSessoes).containsOnly(Collections.emptyList(), valor.getDatasDasSessoes());
        Assertions.assertThat(pacienteFabricado.getValores()).extracting(Valor::getMes).containsOnly(mes, valor.getMes());
        Assertions.assertThat(pacienteFabricado.getValores()).extracting(Valor::getAno).containsOnly(ano, valor.getAno());
        Assertions.assertThat(pacienteFabricado.getTipo()).isEqualTo(tipoDePaciente);
    }

    @Test
    void deveSerPossivelFabricarUmPacienteSemQuantidadeDeDiasSeForDoTipoValorFixo() {
        TipoDePaciente tipoDePaciente = TipoDePaciente.VALOR_FIXO;
        Quantidade quantidadeDeDiasNoMes = null;
        List<LocalDate> datasDasSessoes = Collections.emptyList();
        Mockito.when(pacienteRepositorio.buscar(nome, usuarioId)).thenReturn(null);

        Paciente pacienteFabricado = fabrica.fabricar(usuarioId, nome, endereco, valorPorSessao, mes, ano, tipoDePaciente, datasDasSessoes);

        Assertions.assertThat(pacienteFabricado.getUsuarioId()).isEqualTo(usuarioId);
        Assertions.assertThat(pacienteFabricado.getNome()).isEqualTo(nome);
        Assertions.assertThat(pacienteFabricado.getEndereco()).isEqualTo(endereco);
        Assertions.assertThat(pacienteFabricado.getValores()).extracting(Valor::getValorPorSessao).containsOnly(valorPorSessao);
        Assertions.assertThat(pacienteFabricado.getValores()).extracting(Valor::getQuantidadeDeDiasNoMes).containsOnly(quantidadeDeDiasNoMes);
        Assertions.assertThat(pacienteFabricado.getValores()).extracting(Valor::getMes).containsOnly(mes);
        Assertions.assertThat(pacienteFabricado.getValores()).extracting(Valor::getAno).containsOnly(ano);
        Assertions.assertThat(pacienteFabricado.getTipo()).isEqualTo(tipoDePaciente);
    }

    @Test
    void naoDeveSerPossivelFabricarUmPacienteSemQuantidadeDeDiasSeForDoTipoValorPorSessao() {
        String mensagemEsperada = "Não é possível criar Paciente de valor por sessão sem informar as datas das sessões.";
        TipoDePaciente tipoDePaciente = TipoDePaciente.VALOR_POR_SESSAO;
        List<LocalDate> datasDasSessoes = Collections.emptyList();
        Mockito.when(pacienteRepositorio.buscar(nome, usuarioId)).thenReturn(null);

        Throwable excecaoLancada = Assertions.catchThrowable(() -> fabrica.fabricar(usuarioId, nome, endereco, valorPorSessao, mes, ano, tipoDePaciente, datasDasSessoes));

        Assertions.assertThat(excecaoLancada).hasMessageContaining(mensagemEsperada);
    }

    @Test
    void naoDeveSerPossivelFabricarUmPacienteJahCadastradoSeJahPossuirOMesmoMesEAno() {
        Mes mes = Mes.ABRIL;
        Integer ano = 2023;
        String mensagemEsperada = String.format("Paciente já possui valores referente ao mês %s e ano %s.", mes, ano);
        Valor valor = new ValorBuilder().comMes(mes).comAno(ano).criar();
        Paciente paciente = new PacienteBuilder().comValores(valor).criar();
        Mockito.when(pacienteRepositorio.buscar(nome, usuarioId)).thenReturn(paciente);

        Throwable excecaoLancada = Assertions.catchThrowable(() -> fabrica.fabricar(usuarioId, nome, endereco, valorPorSessao, mes, ano, tipoDePaciente, datasDasSessoes));

        Assertions.assertThat(excecaoLancada).hasMessageContaining(mensagemEsperada);
    }
}