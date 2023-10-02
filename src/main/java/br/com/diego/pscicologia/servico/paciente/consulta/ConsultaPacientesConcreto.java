package br.com.diego.pscicologia.servico.paciente.consulta;

import br.com.diego.pscicologia.dominio.paciente.Paciente;
import br.com.diego.pscicologia.dominio.paciente.PacienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultaPacientesConcreto implements ConsultaPacientes {

    private final PacienteRepositorio pacienteRepositorio;

    @Autowired
    public ConsultaPacientesConcreto(PacienteRepositorio pacienteRepositorio) {
        this.pacienteRepositorio = pacienteRepositorio;
    }

    @Override
    public List<PacienteDTO> consultar(FiltroDeConsultaDePaciente filtro) throws Exception {
        List<Paciente> pacientes = pacienteRepositorio.findAll();
        validarPacientesObtidos(pacientes);

        return montarDTOs(pacientes);
    }

    private void validarPacientesObtidos(List<Paciente> pacientes) throws Exception {
        if (pacientes.isEmpty()) {
            throw new Exception("Não foi possível encontrar nenhum paciente cadastrado.");
        }
    }

    private List<PacienteDTO> montarDTOs(List<Paciente> pacientes) {
        return pacientes.stream().map(paciente -> {
            try {
                return new MontadorDePacienteDTO().montar(paciente);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }


}
