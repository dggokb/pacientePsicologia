package br.com.diego.psicologia.servico.paciente.altera;

import br.com.diego.psicologia.builder.PacienteBuilder;
import br.com.diego.psicologia.dominio.paciente.Paciente;
import br.com.diego.psicologia.dominio.paciente.PacienteRepositorio;
import br.com.diego.psicologia.dominio.paciente.ServicoParaAlterarValorDoPacienteReferenteAoMesEAno;
import br.com.diego.psicologia.dominio.paciente.TipoDePaciente;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.*;

class AlteraPacienteTest {

    private PacienteRepositorio pacienteRepositorio;
    private AlteraPaciente alteraPaciente;
    private String id;
    private String nome;
    private String endereco;
    private BigDecimal valorPorSessao;
    private String mes;
    private Integer ano;
    private String tipo;
    private List<Date> datasDasSessoes = new ArrayList<>();
    private ServicoParaAlterarValorDoPacienteReferenteAoMesEAno servicoParaAlterarValorDoPacienteReferenteAoMesEAno;

    @BeforeEach
    void setUp() {
        pacienteRepositorio = Mockito.mock(PacienteRepositorio.class);
        servicoParaAlterarValorDoPacienteReferenteAoMesEAno = new ServicoParaAlterarValorDoPacienteReferenteAoMesEAno();
        alteraPaciente = new AlteraPacienteConcreto(pacienteRepositorio, servicoParaAlterarValorDoPacienteReferenteAoMesEAno);
        id = UUID.randomUUID().toString();
        nome = "Diego";
        endereco = "Novo endereço do paciente";
        valorPorSessao = BigDecimal.TEN;
        mes = "JANEIRO";
        ano = 2023;
        tipo = TipoDePaciente.VALOR_FIXO.name();
        datasDasSessoes = Collections.singletonList(new Date());
    }

    @Test
    void deveSerPossivelAlterarOsDadosDeUmPaciente() throws Exception {
        String enderecoEsperado = "Novo endereço do paciente";
        BigDecimal novoValorDaSessao = BigDecimal.TEN;
        Paciente paciente = new PacienteBuilder().criar();
        AlterarPaciente comando = new AlterarPaciente(id, paciente.getNome(), enderecoEsperado, novoValorDaSessao,
                paciente.getValores().get(0).getMes().name(), paciente.getValores().get(0).getAno(), paciente.getTipo().name(), datasDasSessoes);
        Mockito.when(pacienteRepositorio.findById(id)).thenReturn(Optional.ofNullable(paciente));

        alteraPaciente.executar(comando);

        Mockito.verify(pacienteRepositorio).save(paciente);
        Assertions.assertThat(paciente.getEndereco()).isEqualTo(enderecoEsperado);
    }

    @Test
    void naoDeveSerPossivelAlterarOsDadosDeUmPacienteSeNaoEncontrarOPaciente() {
        String mensagemEsperada = "Não foi possível encontrar o paciente para alteração.";
        AlterarPaciente comando = new AlterarPaciente(id, nome, endereco, valorPorSessao, mes, ano, tipo, datasDasSessoes);
        Mockito.when(pacienteRepositorio.findById(id)).thenReturn(Optional.empty());

        Throwable excecaoLancada = Assertions.catchThrowable(() -> alteraPaciente.executar(comando));

        Assertions.assertThat(excecaoLancada).hasMessageContaining(mensagemEsperada);
    }
}