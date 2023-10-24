package br.com.diego.pscicologia.dominio.paciente;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

public class ServicoParaObterPacientesPorNome {

    private final PacienteRepositorio pacienteRepositorio;

    @Autowired
    public ServicoParaObterPacientesPorNome(PacienteRepositorio pacienteRepositorio) {
        this.pacienteRepositorio = pacienteRepositorio;
    }

    public List<Paciente> obter(String nome) {
        if (Objects.isNull(nome) || nome.isEmpty()) {
            return pacienteRepositorio.findAll();
        }
        return pacienteRepositorio.buscarTodos(nome);
    }
}
