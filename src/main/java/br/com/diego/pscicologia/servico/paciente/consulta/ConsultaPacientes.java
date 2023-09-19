package br.com.diego.pscicologia.servico.paciente.consulta;

import java.util.List;

public interface ConsultaPacientes {
    List<PacienteDTO> buscarTodos() throws Exception;
}