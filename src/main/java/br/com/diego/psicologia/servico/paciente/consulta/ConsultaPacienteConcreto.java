package br.com.diego.psicologia.servico.paciente.consulta;


import br.com.diego.psicologia.dominio.paciente.Paciente;
import br.com.diego.psicologia.dominio.paciente.ServicoParaObterPacientesDeUmUsuarioPorNome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultaPacienteConcreto implements ConsultaPaciente {

    private final ServicoParaObterPacientesDeUmUsuarioPorNome servicoParaObterPacientesDeUmUsuarioPorNome;

    @Autowired
    public ConsultaPacienteConcreto(ServicoParaObterPacientesDeUmUsuarioPorNome servicoParaObterPacientesDeUmUsuarioPorNome) {
        this.servicoParaObterPacientesDeUmUsuarioPorNome = servicoParaObterPacientesDeUmUsuarioPorNome;
    }

    @Override
    public List<PacienteDTO> consultar(FiltroDeConsultaDePaciente filtro) {
        List<Paciente> pacientes = servicoParaObterPacientesDeUmUsuarioPorNome.obter(filtro.getNome(), filtro.getUsuarioId());
        return criarDTOs(pacientes);
    }

    private List<PacienteDTO> criarDTOs(List<Paciente> pacientes) {
        return pacientes.stream().map(paciente -> new MontadorDePacienteDTO().montar(paciente)).collect(Collectors.toList());
    }
}
