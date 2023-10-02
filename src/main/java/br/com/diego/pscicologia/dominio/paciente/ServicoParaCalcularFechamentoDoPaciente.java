package br.com.diego.pscicologia.dominio.paciente;

import br.com.diego.pscicologia.comum.ExcecaoDeCampoObrigatorio;
import br.com.diego.pscicologia.comum.ExcecaoDeRegraDeNegocio;
import br.com.diego.pscicologia.comum.Mes;
import br.com.diego.pscicologia.comum.Moeda;

import java.util.Optional;

public class ServicoParaCalcularFechamentoDoPaciente {

    private PacienteRepositorio pacienteRepositorio;

    public ServicoParaCalcularFechamentoDoPaciente(PacienteRepositorio pacienteRepositorio) {
        this.pacienteRepositorio = pacienteRepositorio;
    }

    public Moeda calcular(String id, Mes mes, Integer ano) {
        validarCamposObrigatorios(id, mes, ano);
        Optional<Paciente> paciente = pacienteRepositorio.findById(id);
        validarPacienteObtido(paciente);
        //TODO: ver uma forma de obter diretamente do repo
        Optional<Valor> valorDoMesEAno = paciente.get().getValores().stream().filter(valor -> valor.ehDoMesmo(mes, ano)).findFirst();
        validarValorObtido(mes, ano, valorDoMesEAno);

        return valorDoMesEAno.get().obterValorTotal();
    }

    private void validarCamposObrigatorios(String id, Mes mes, Integer ano) {
        new ExcecaoDeCampoObrigatorio()
                .quandoNulo(id, "É necessário informar o paciente para obter o valor do fechamento.")
                .quandoNulo(mes, "É necessário informar o mês para obter o valor do fechamento.")
                .quandoNulo(ano, "É necessário informar o ano para obter o valor do fechamento.")
                .entaoDispara();
    }

    private void validarPacienteObtido(Optional<Paciente> paciente) {
        if (paciente.isEmpty()) {
            throw new ExcecaoDeRegraDeNegocio("Não foi possível encontar o paciente para obter o valor do fechamento.");
        }
    }

    private void validarValorObtido(Mes mes, Integer ano, Optional<Valor> valorDoMesEAno) {
        if (valorDoMesEAno.isEmpty()) {
            throw new ExcecaoDeRegraDeNegocio(String.format("Não foi possível encontrar o valor do mês %s do ano %s", mes, ano));
        }
    }
}
