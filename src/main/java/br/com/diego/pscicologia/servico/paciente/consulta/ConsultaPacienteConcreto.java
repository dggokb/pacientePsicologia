package br.com.diego.pscicologia.servico.paciente.consulta;


import br.com.diego.pscicologia.dominio.paciente.Paciente;
import br.com.diego.pscicologia.dominio.paciente.ServicoParaObterPacientesPorNome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultaPacienteConcreto implements ConsultaPaciente {

    private final ServicoParaObterPacientesPorNome servicoParaObterPacientesPorNome;

    @Autowired
    public ConsultaPacienteConcreto(ServicoParaObterPacientesPorNome servicoParaObterPacientesPorNome) {
        this.servicoParaObterPacientesPorNome = servicoParaObterPacientesPorNome;
    }

    @Override
    public List<PacienteDTO> consultar(FiltroDeConsultaDePaciente filtro) {
        List<Paciente> pacientes = servicoParaObterPacientesPorNome.obter(filtro.getNome());
        return criarDTOs(pacientes);
    }

    private List<PacienteDTO> criarDTOs(List<Paciente> pacientes) {
        return pacientes.stream().map(paciente -> new MontadorDePacienteDTO().montar(paciente)).collect(Collectors.toList());
    }
}
