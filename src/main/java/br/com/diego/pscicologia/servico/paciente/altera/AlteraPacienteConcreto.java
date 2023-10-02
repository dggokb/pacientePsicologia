package br.com.diego.pscicologia.servico.paciente.altera;

import br.com.diego.pscicologia.dominio.paciente.Paciente;
import br.com.diego.pscicologia.dominio.paciente.PacienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AlteraPacienteConcreto implements AlteraPaciente {

    private final PacienteRepositorio pacienteRepositorio;

    @Autowired
    public AlteraPacienteConcreto(PacienteRepositorio pacienteRepositorio) {
        this.pacienteRepositorio = pacienteRepositorio;
    }

    @Override
    public String executar(AlterarPaciente comando) {
        Optional<Paciente> pacienteObtido = pacienteRepositorio.findById(comando.getId());
        try {
            validarPacienteObtido(pacienteObtido);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        pacienteObtido.get().alterar(comando.getEndereco());
        pacienteRepositorio.save(pacienteObtido.get());

        return pacienteObtido.get().getId();
    }

    private void validarPacienteObtido(Optional<Paciente> pacienteObtido) throws Exception {
        if (pacienteObtido.isEmpty()) {
            throw new Exception("Não foi possível encontrar o paciente para alteração.");
        }
    }
}
