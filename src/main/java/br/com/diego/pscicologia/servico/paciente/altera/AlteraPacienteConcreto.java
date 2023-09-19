package br.com.diego.pscicologia.servico.paciente.altera;

import br.com.diego.pscicologia.dominio.paciente.Paciente;
import br.com.diego.pscicologia.dominio.paciente.PacienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AlteraPacienteConcreto implements AlteraPaciente {

    private final PacienteRepositorio pacienteRepositorio;

    @Autowired
    public AlteraPacienteConcreto(PacienteRepositorio pacienteRepositorio) {
        this.pacienteRepositorio = pacienteRepositorio;
    }

    @Override
    public void alterar(AlterarPaciente comando) throws Exception {
        Optional<Paciente> pacienteObtido = pacienteRepositorio.findById(comando.getId());
        validarPacienteObtido(pacienteObtido);
        pacienteObtido.get().alterar(comando.getEndereco(), comando.getValorPorSessao());
        pacienteRepositorio.save(pacienteObtido.get());
    }

    private static void validarPacienteObtido(Optional<Paciente> pacienteObtido) throws Exception {
        if(pacienteObtido.isEmpty()){
            throw new Exception("Não foi possível encontrar o paciente para alteração.");
        }
    }
}
