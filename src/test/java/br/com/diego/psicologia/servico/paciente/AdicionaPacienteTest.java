package br.com.diego.psicologia.servico.paciente;

import br.com.diego.psicologia.builder.PacienteBuilder;
import br.com.diego.psicologia.dominio.paciente.Paciente;
import br.com.diego.psicologia.dominio.paciente.PacienteRepositorio;
import br.com.diego.psicologia.dominio.paciente.TipoDePaciente;
import br.com.diego.psicologia.dominio.paciente.Valor;
import br.com.diego.psicologia.servico.paciente.adiciona.AdicionaPaciente;
import br.com.diego.psicologia.servico.paciente.adiciona.AdicionaPacienteConcreto;
import br.com.diego.psicologia.servico.paciente.adiciona.AdicionarPaciente;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class AdicionaPacienteTest {

    private PacienteRepositorio pacienteRepositorio;
    private AdicionaPaciente adicionaPaciente;


    @BeforeEach
    void setUp() {
        pacienteRepositorio = Mockito.mock(PacienteRepositorio.class);
        adicionaPaciente = new AdicionaPacienteConcreto(pacienteRepositorio);
    }

    @Test
    void deveSerPossivelAdicionarUmPaciente() throws Exception {
        Paciente paciente = new PacienteBuilder().comTipo(TipoDePaciente.VALOR_FIXO).criar();
        Valor valor = paciente.getValores().get(0);
        List<Date> datasDasSessoes = Collections.singletonList(new Date());
        AdicionarPaciente comando = new AdicionarPaciente(paciente.getUsuarioId(), paciente.getNome(), paciente.getEndereco(),
                valor.getValorPorSessao().valor(),
                valor.getMes().name(), valor.getAno(), paciente.getTipo().name(), datasDasSessoes);
        ArgumentCaptor<Paciente> pacienteCaptor = ArgumentCaptor.forClass(Paciente.class);
        Mockito.when(pacienteRepositorio.save(pacienteCaptor.capture())).thenReturn(paciente);

        adicionaPaciente.executar(comando);

        Paciente pacienteCapturado = pacienteCaptor.getValue();
        Mockito.verify(pacienteRepositorio).save(pacienteCaptor.capture());
        Assertions.assertThat(pacienteCapturado.getUsuarioId()).isEqualTo(paciente.getUsuarioId());
        Assertions.assertThat(pacienteCapturado.getNome()).isEqualTo(paciente.getNome());
        Assertions.assertThat(pacienteCapturado.getEndereco()).isEqualTo(paciente.getEndereco());
        Assertions.assertThat(pacienteCapturado.getDataDeInicio()).isEqualTo(paciente.getDataDeInicio());
        Assertions.assertThat(pacienteCapturado.getValores()).usingRecursiveFieldByFieldElementComparator().containsOnly(valor);
        Assertions.assertThat(pacienteCapturado.getTipo()).isEqualTo(paciente.getTipo());
    }
}