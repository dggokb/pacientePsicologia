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

    @BeforeEach
    void setUp() {
        pacienteRepositorio = Mockito.mock(PacienteRepositorio.class);
        alteraPaciente = new AlteraPacienteConcreto(pacienteRepositorio);
        id = UUID.randomUUID().toString();
        endereco = "Novo endereço do paciente";
    }

    @Test
    void deveSerPossivelAlterarOsDadosDeUmPaciente() throws Exception {
        String enderecoEsperado = "Novo endereço do paciente";
        AlterarPaciente comando = new AlterarPaciente(id, enderecoEsperado);
        Paciente paciente = new PacienteBuilder().criar();
        Mockito.when(pacienteRepositorio.findById(id)).thenReturn(Optional.ofNullable(paciente));

        alteraPaciente.executar(comando);

        Mockito.verify(pacienteRepositorio).save(paciente);
        Assertions.assertThat(paciente.getEndereco()).isEqualTo(enderecoEsperado);
    }

    @Test
    void naoDeveSerPossivelAlterarOsDadosDeUmPacienteSeNaoEncontrarOPaciente() throws Exception {
        String mensagemEsperada = "Não foi possível encontrar o paciente para alteração.";
        AlterarPaciente comando = new AlterarPaciente(id, endereco);
        Mockito.when(pacienteRepositorio.findById(id)).thenReturn(Optional.empty());

        Throwable excecaoLancada = Assertions.catchThrowable(() -> alteraPaciente.executar(comando));

        Assertions.assertThat(excecaoLancada).hasMessageContaining(mensagemEsperada);
    }
}