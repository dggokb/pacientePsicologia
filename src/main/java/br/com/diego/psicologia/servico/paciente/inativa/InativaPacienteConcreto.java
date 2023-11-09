package br.com.diego.psicologia.servico.paciente.inativa;

import br.com.diego.psicologia.dominio.paciente.Paciente;
import br.com.diego.psicologia.dominio.paciente.PacienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class InativaPacienteConcreto implements InativaPaciente {

    private final PacienteRepositorio pacienteRepositorio;

    @Autowired
    public InativaPacienteConcreto(PacienteRepositorio pacienteRepositorio) {
        this.pacienteRepositorio = pacienteRepositorio;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void inativar(String id) throws Exception {
        Optional<Paciente> pacienteObtido = pacienteRepositorio.findById(id);
        validarPacienteObtido(pacienteObtido);
        pacienteObtido.get().inativar();
        pacienteRepositorio.save(pacienteObtido.get());
    }

    private static void validarPacienteObtido(Optional<Paciente> pacienteObtido) throws Exception {
        if (pacienteObtido.isEmpty()) {
            throw new Exception("Não foi possível encontrar o paciente para inativar.");
        }
    }
}
