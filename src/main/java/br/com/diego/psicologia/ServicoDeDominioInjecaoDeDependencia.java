package br.com.diego.psicologia;

import br.com.diego.psicologia.dominio.paciente.PacienteRepositorio;
import br.com.diego.psicologia.dominio.paciente.ServicoParaCalcularFechamentoDoPaciente;
import br.com.diego.psicologia.dominio.paciente.ServicoParaAlterarValorDoPacienteReferenteAoMesEAno;
import br.com.diego.psicologia.dominio.paciente.ServicoParaObterPacientesDeUmUsuarioPorNome;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicoDeDominioInjecaoDeDependencia {

    @Bean
    public ServicoParaCalcularFechamentoDoPaciente servicoParaCalcularFechamentoDoPaciente(PacienteRepositorio pacienteRepositorio) {
        return new ServicoParaCalcularFechamentoDoPaciente(pacienteRepositorio);
    }

    @Bean
    public ServicoParaObterPacientesDeUmUsuarioPorNome servicoParaObterPaciente(PacienteRepositorio pacienteRepositorio) {
        return new ServicoParaObterPacientesDeUmUsuarioPorNome(pacienteRepositorio);
    }

    @Bean
    public ServicoParaAlterarValorDoPacienteReferenteAoMesEAno servicoParaCriarNovoValorDoPacienteReferenteAoMesEAno() {
        return new ServicoParaAlterarValorDoPacienteReferenteAoMesEAno();
    }
}
