package br.com.diego.pscicologia.porta.adaptador.persistencia.paciente;

import br.com.diego.pscicologia.builder.PacienteBuilder;
import br.com.diego.pscicologia.dominio.paciente.Paciente;
import br.com.diego.pscicologia.dominio.paciente.PacienteRepositorio;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Arrays;
import java.util.List;

@DataMongoTest
public class PacienteRepositorioMongoDbTest {

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @BeforeEach
    void setUp() {
        pacienteRepositorio.deleteAll();
    }

    @Test
    public void deveSerPossivelBuscarSomentePacientesAtivos() {
        Paciente pacienteAtivo = new PacienteBuilder().ativo().criar();
        Paciente outroPacienteAtivo = new PacienteBuilder().ativo().criar();
        Paciente pacienteInativo = new PacienteBuilder().inativo().criar();
        List<Paciente> pacientes = Arrays.asList(pacienteAtivo, outroPacienteAtivo, pacienteInativo);
        pacienteRepositorio.saveAll(pacientes);

        List<Paciente> pacientesObtidos = pacienteRepositorio.buscarAtivos();

        Assertions.assertThat(pacientesObtidos).usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(pacienteAtivo, outroPacienteAtivo);
    }

    @Test
    public void deveSerPossivelBuscarSomentePacientesInativos() {
        Paciente pacienteInativo = new PacienteBuilder().inativo().criar();
        Paciente outroPacienteInativo = new PacienteBuilder().inativo().criar();
        Paciente pacienteAtivo = new PacienteBuilder().ativo().criar();
        List<Paciente> pacientes = Arrays.asList(pacienteInativo, outroPacienteInativo, pacienteAtivo);
        pacienteRepositorio.saveAll(pacientes);

        List<Paciente> pacientesObtidos = pacienteRepositorio.buscarInativos();

        Assertions.assertThat(pacientesObtidos).usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(pacienteInativo, outroPacienteInativo);
    }
}

