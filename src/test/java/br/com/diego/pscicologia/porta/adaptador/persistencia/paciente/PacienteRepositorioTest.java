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
        Paciente pacienteAtivoValorPorSessao = new PacienteBuilder().ativo().criarTipoValorPorSessao();
        Paciente pacienteAtivoFixo = new PacienteBuilder().ativo().criarTipoFixo();
        Paciente pacienteInativo = new PacienteBuilder().inativo().criarTipoValorPorSessao();
        List<Paciente> pacientes = Arrays.asList(pacienteAtivoValorPorSessao, pacienteAtivoFixo, pacienteInativo);
        pacienteRepositorio.saveAll(pacientes);

        List<Paciente> pacientesObtidos = pacienteRepositorio.buscarAtivos();

        Assertions.assertThat(pacientesObtidos).usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(pacienteAtivoValorPorSessao, pacienteAtivoFixo);
    }

    @Test
    public void deveSerPossivelBuscarSomentePacientesInativos() {
        Paciente pacienteInativoValorPoSessao = new PacienteBuilder().inativo().criarTipoValorPorSessao();
        Paciente pacienteInativoFixo = new PacienteBuilder().inativo().criarTipoFixo();
        Paciente pacienteAtivo = new PacienteBuilder().ativo().criarTipoValorPorSessao();
        List<Paciente> pacientes = Arrays.asList(pacienteInativoValorPoSessao, pacienteInativoFixo, pacienteAtivo);
        pacienteRepositorio.saveAll(pacientes);

        List<Paciente> pacientesObtidos = pacienteRepositorio.buscarInativos();

        Assertions.assertThat(pacientesObtidos).usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(pacienteInativoValorPoSessao, pacienteInativoFixo);
    }
}

