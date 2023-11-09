package br.com.diego.psicologia.porta.adaptador.persistencia.paciente;

import br.com.diego.psicologia.builder.PacienteBuilder;
import br.com.diego.psicologia.dominio.paciente.Paciente;
import br.com.diego.psicologia.dominio.paciente.PacienteRepositorio;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Arrays;
import java.util.List;

@DataMongoTest
public class PacienteRepositorioTest {

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    //TODO: verificar forma de criar em memória banco
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

    @Test
    public void deveSerPossivelBuscarPacientePorNome() {
        String nomeParaBuscar = "Mario Esteban";
        Paciente paciente = new PacienteBuilder().comNome(nomeParaBuscar).criar();
        Paciente outroPaciente = new PacienteBuilder().criar();
        List<Paciente> pacientes = Arrays.asList(paciente, outroPaciente);
        pacienteRepositorio.saveAll(pacientes);

        Paciente pacienteObtido = pacienteRepositorio.buscar(nomeParaBuscar);

        Assertions.assertThat(pacienteObtido).usingRecursiveComparison().isEqualTo(paciente);
    }

    @Test
    public void naoDeveSerPossivelBuscarPacientePorNomeSeONomeNaoForIgual() {
        String nomeParaBuscar = "Mario Esteban";
        String nomeIncompleto = "Mario";
        Paciente paciente = new PacienteBuilder().comNome(nomeParaBuscar).criar();
        pacienteRepositorio.save(paciente);

        Paciente pacienteObtido = pacienteRepositorio.buscar(nomeIncompleto);

        Assertions.assertThat(pacienteObtido).isNull();
    }
}

