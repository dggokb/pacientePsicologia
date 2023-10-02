package br.com.diego.pscicologia.servico.paciente;

import br.com.diego.pscicologia.builder.PacienteBuilder;
import br.com.diego.pscicologia.dominio.paciente.Paciente;
import br.com.diego.pscicologia.dominio.paciente.PacienteRepositorio;
import br.com.diego.pscicologia.dominio.paciente.Valor;
import br.com.diego.pscicologia.servico.paciente.adiciona.AdicionaPaciente;
import br.com.diego.pscicologia.servico.paciente.adiciona.AdicionaPacienteConcreto;
import br.com.diego.pscicologia.servico.paciente.adiciona.AdicionarPaciente;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Optional;

public class AdicionaPacienteTest {

    private PacienteRepositorio pacienteRepositorio;
    private AdicionaPaciente adicionaPaciente;


    @BeforeEach
    void setUp() {
        pacienteRepositorio = Mockito.mock(PacienteRepositorio.class);
        adicionaPaciente = new AdicionaPacienteConcreto(pacienteRepositorio);
    }

    @Test
    void deveSerPossivelAdicionarUmPaciente() {
        Paciente paciente = new PacienteBuilder().criar();
        Valor valor = paciente.getValores().get(0);
        AdicionarPaciente comando = new AdicionarPaciente(paciente.getNome(), paciente.getEndereco(),
                Optional.of(valor.getQuantidadeDeDiasNoMes().quantidade()), valor.getValorPorSessao().valor(),
                valor.getMes().name(), valor.getAno(), paciente.getTipo().name());
        ArgumentCaptor<Paciente> pacienteCaptor = ArgumentCaptor.forClass(Paciente.class);
        Mockito.when(pacienteRepositorio.save(pacienteCaptor.capture())).thenReturn(paciente);

        adicionaPaciente.executar(comando);

        Paciente pacienteCapturado = pacienteCaptor.getValue();
        Mockito.verify(pacienteRepositorio).save(pacienteCaptor.capture());
        Assertions.assertThat(pacienteCapturado.getNome()).isEqualTo(paciente.getNome());
        Assertions.assertThat(pacienteCapturado.getEndereco()).isEqualTo(paciente.getEndereco());
        Assertions.assertThat(pacienteCapturado.getDataDeInicio()).isEqualTo(paciente.getDataDeInicio());
        Assertions.assertThat(pacienteCapturado.getValores()).usingRecursiveFieldByFieldElementComparator().containsOnly(valor);
        Assertions.assertThat(pacienteCapturado.getTipo()).isEqualTo(paciente.getTipo());
    }
}