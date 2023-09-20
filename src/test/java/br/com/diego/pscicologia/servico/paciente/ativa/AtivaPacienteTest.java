package br.com.diego.pscicologia.servico.paciente.ativa;

import br.com.diego.pscicologia.builder.PacienteBuilder;
import br.com.diego.pscicologia.dominio.paciente.Paciente;
import br.com.diego.pscicologia.dominio.paciente.PacienteRepositorio;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

class AtivaPacienteTest {

    private PacienteRepositorio pacienteRepositorio;
    private AtivaPaciente ativaPaciente;
    private String id;

    @BeforeEach
    void setUp() {
        pacienteRepositorio = Mockito.mock(PacienteRepositorio.class);
        ativaPaciente = new AtivaPacienteConcreto(pacienteRepositorio);
        id = UUID.randomUUID().toString();
    }

    @Test
    void deveSerPossivelAtivarUmPaciente() throws Exception {
        Paciente paciente = new PacienteBuilder().inativo().criarTipoMensal();
        Mockito.when(pacienteRepositorio.findById(id)).thenReturn(Optional.ofNullable(paciente));

        ativaPaciente.ativar(id);

        Mockito.verify(pacienteRepositorio).save(paciente);
        Assertions.assertThat(paciente.getInativo()).isFalse();
    }

    @Test
    void naoDeveSerPossivelAtivarUmPacienteSeNaoEncontrarOPaciente() throws Exception {
        String mensagemEsperda = "Não foi possível encontrar o paciente para ativar.";
        Mockito.when(pacienteRepositorio.findById(id)).thenReturn(Optional.empty());

        Throwable excecaoLancada = Assertions.catchThrowable(() -> ativaPaciente.ativar(id));

        Assertions.assertThat(excecaoLancada).hasMessageContaining(mensagemEsperda);
    }
}