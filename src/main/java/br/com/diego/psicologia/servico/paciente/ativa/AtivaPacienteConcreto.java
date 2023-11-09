package br.com.diego.psicologia.servico.paciente.ativa;

import br.com.diego.psicologia.dominio.paciente.Paciente;
import br.com.diego.psicologia.dominio.paciente.PacienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AtivaPacienteConcreto implements AtivaPaciente {

    private final PacienteRepositorio pacienteRepositorio;

    @Autowired
    public AtivaPacienteConcreto(PacienteRepositorio pacienteRepositorio) {
        this.pacienteRepositorio = pacienteRepositorio;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void ativar(String id) throws Exception {
        Optional<Paciente> pacienteObtido = pacienteRepositorio.findById(id);
        validarPacienteObtido(pacienteObtido);
        pacienteObtido.get().ativar();
        pacienteRepositorio.save(pacienteObtido.get());
    }

    private static void validarPacienteObtido(Optional<Paciente> pacienteObtido) throws Exception {
        if (pacienteObtido.isEmpty()) {
            throw new Exception("Não foi possível encontrar o paciente para ativar.");
        }
    }
}
