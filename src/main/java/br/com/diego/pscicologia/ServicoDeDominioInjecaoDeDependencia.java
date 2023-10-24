package br.com.diego.pscicologia;

import br.com.diego.pscicologia.dominio.paciente.PacienteRepositorio;
import br.com.diego.pscicologia.dominio.paciente.ServicoParaCalcularFechamentoDoPaciente;
import br.com.diego.pscicologia.dominio.paciente.ServicoParaObterPacientesPorNome;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicoDeDominioInjecaoDeDependencia {

    @Bean
    public ServicoParaCalcularFechamentoDoPaciente servicoParaCalcularFechamentoDoPaciente(PacienteRepositorio pacienteRepositorio) {
        return new ServicoParaCalcularFechamentoDoPaciente(pacienteRepositorio);
    }

    @Bean
    public ServicoParaObterPacientesPorNome servicoParaObterPaciente(PacienteRepositorio pacienteRepositorio) {
        return new ServicoParaObterPacientesPorNome(pacienteRepositorio);
    }
}
