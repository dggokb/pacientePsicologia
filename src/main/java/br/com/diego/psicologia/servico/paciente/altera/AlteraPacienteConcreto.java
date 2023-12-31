package br.com.diego.psicologia.servico.paciente.altera;

import br.com.diego.psicologia.dominio.paciente.Paciente;
import br.com.diego.psicologia.dominio.paciente.PacienteRepositorio;
import br.com.diego.psicologia.dominio.paciente.ServicoParaAlterarValorDoPacienteReferenteAoMesEAno;
import br.com.diego.psicologia.dominio.paciente.Valor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlteraPacienteConcreto implements AlteraPaciente {

    private final PacienteRepositorio pacienteRepositorio;
    private final ServicoParaAlterarValorDoPacienteReferenteAoMesEAno servicoParaAlterarValorDoPacienteReferenteAoMesEAno;

    @Autowired
    public AlteraPacienteConcreto(PacienteRepositorio pacienteRepositorio,
                                  ServicoParaAlterarValorDoPacienteReferenteAoMesEAno servicoParaAlterarValorDoPacienteReferenteAoMesEAno) {
        this.pacienteRepositorio = pacienteRepositorio;
        this.servicoParaAlterarValorDoPacienteReferenteAoMesEAno = servicoParaAlterarValorDoPacienteReferenteAoMesEAno;
    }

    @Override
    public String executar(AlterarPaciente comando) {
        Optional<Paciente> pacienteObtido = pacienteRepositorio.findById(comando.getId());
        validarPacienteObtido(pacienteObtido);
        List<Valor> novosValoresDoMesEAno = criarNovosValores(comando, pacienteObtido);
        pacienteObtido.get().alterar(comando.getNome(),
                comando.getEndereco(),
                comando.getTipoDePaciente(),
                novosValoresDoMesEAno);
        pacienteRepositorio.save(pacienteObtido.get());

        return pacienteObtido.get().getId();
    }

    private void validarPacienteObtido(Optional<Paciente> pacienteObtido) {
        if (pacienteObtido.isEmpty()) {
            throw new IllegalArgumentException("Não foi possível encontrar o paciente para alteração.");
        }
    }

    private List<Valor> criarNovosValores(AlterarPaciente comando, Optional<Paciente> pacienteObtido) {
        return servicoParaAlterarValorDoPacienteReferenteAoMesEAno.alterar(pacienteObtido.get().getValores(),
                comando.getValorPorSessao(),
                comando.getMes(),
                comando.getAno(),
                comando.getTipoDePaciente(),
                comando.getDatasDasSessoes());
    }
}
