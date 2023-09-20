package br.com.diego.pscicologia.servico.paciente;

import br.com.diego.pscicologia.builder.PacienteBuilder;
import br.com.diego.pscicologia.dominio.paciente.Paciente;
import br.com.diego.pscicologia.dominio.paciente.PacienteRepositorio;
import br.com.diego.pscicologia.servico.paciente.inativa.InativaPaciente;
import br.com.diego.pscicologia.servico.paciente.inativa.InativaPacienteConcreto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

class InativaPacienteTest {

    private PacienteRepositorio pacienteRepositorio;
    private InativaPaciente inativaPaciente;

    @BeforeEach
    void setUp() {
        pacienteRepositorio = Mockito.mock(PacienteRepositorio.class);
        inativaPaciente = new InativaPacienteConcreto(pacienteRepositorio);
    }

    @Test
    void deveSerPossivelInativarUmPaciente() throws Exception {
        String id = UUID.randomUUID().toString();
        Paciente paciente = new PacienteBuilder().ativo().criarTipoValorPorSessao();
        Mockito.when(pacienteRepositorio.findById(id)).thenReturn(Optional.ofNullable(paciente));

        inativaPaciente.inativar(id);

        Mockito.verify(pacienteRepositorio).save(paciente);
        Assertions.assertThat(paciente.getInativo()).isTrue();
    }

    @Test
    void naoDeveSerPossivelInativarUmPacienteSeNaoEncontrarOPaciente() throws Exception {
        String mensagemEsperada = "Não foi possível encontrar o paciente para inativar.";
        String id = UUID.randomUUID().toString();
        Mockito.when(pacienteRepositorio.findById(id)).thenReturn(Optional.empty());

        Throwable excecaoLancada = Assertions.catchThrowable(() -> inativaPaciente.inativar(id));

        Assertions.assertThat(excecaoLancada).hasMessageContaining(mensagemEsperada);
    }
}