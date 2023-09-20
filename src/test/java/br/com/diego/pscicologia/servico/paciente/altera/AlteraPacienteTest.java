package br.com.diego.pscicologia.servico.paciente.altera;

import br.com.diego.pscicologia.builder.PacienteBuilder;
import br.com.diego.pscicologia.dominio.paciente.Paciente;
import br.com.diego.pscicologia.dominio.paciente.PacienteRepositorio;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

class AlteraPacienteTest {

    private PacienteRepositorio pacienteRepositorio;
    private AlteraPaciente alteraPaciente;
    private String id;
    private String endereco;
    private BigDecimal valorPorSessao;

    @BeforeEach
    void setUp() {
        pacienteRepositorio = Mockito.mock(PacienteRepositorio.class);
        alteraPaciente = new AlteraPacienteConcreto(pacienteRepositorio);
        id = UUID.randomUUID().toString();
        endereco = "Novo endereço do paciente";
        valorPorSessao = BigDecimal.valueOf(200);
    }

    @Test
    void deveSerPossivelAlterarOsDadosDeUmPaciente() throws Exception {
        String enderecoEsperado = "Novo endereço do paciente";
        BigDecimal valorPorSessaoEsperado = BigDecimal.valueOf(200);
        AlterarPaciente comando = new AlterarPaciente(id, enderecoEsperado, valorPorSessaoEsperado);
        Paciente paciente = new PacienteBuilder().criarTipoMensal();
        Mockito.when(pacienteRepositorio.findById(id)).thenReturn(Optional.ofNullable(paciente));

        alteraPaciente.alterar(comando);

        Mockito.verify(pacienteRepositorio).save(paciente);
        Assertions.assertThat(paciente.getEndereco()).isEqualTo(enderecoEsperado);
        Assertions.assertThat(paciente.getValorPorSessao().valor()).isEqualTo(valorPorSessaoEsperado);
    }

    @Test
    void naoDeveSerPossivelAlterarOsDadosDeUmPacienteSeNaoEncontrarOPaciente() throws Exception {
        String mensagemEsperada = "Não foi possível encontrar o paciente para alteração.";
        AlterarPaciente comando = new AlterarPaciente(id, endereco, valorPorSessao);
        Mockito.when(pacienteRepositorio.findById(id)).thenReturn(Optional.empty());

        Throwable excecaoLancada = Assertions.catchThrowable(() -> alteraPaciente.alterar(comando));

        Assertions.assertThat(excecaoLancada).hasMessageContaining(mensagemEsperada);
    }
}