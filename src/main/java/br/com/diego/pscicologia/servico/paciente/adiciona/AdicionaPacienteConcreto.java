package br.com.diego.pscicologia.servico.paciente.adiciona;

import br.com.diego.pscicologia.dominio.paciente.Paciente;
import br.com.diego.pscicologia.dominio.paciente.PacienteFabrica;
import br.com.diego.pscicologia.dominio.paciente.PacienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdicionaPacienteConcreto implements AdicionaPaciente {

    private final PacienteRepositorio pacienteRepositorio;

    @Autowired
    public AdicionaPacienteConcreto(PacienteRepositorio pacienteRepositorio) {
        this.pacienteRepositorio = pacienteRepositorio;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String adicionar(AdicionarPaciente comando) {
        Paciente paciente = new PacienteFabrica(pacienteRepositorio).fabricar(comando.getNome(),
                comando.getEndereco(),
                comando.getQuantidadeDeDiasNoMes(),
                comando.getValorPorSessao(),
                comando.getMes(),
                comando.getAno(),
                comando.getTipo());
        pacienteRepositorio.save(paciente);

        return paciente.getId();
    }
}
