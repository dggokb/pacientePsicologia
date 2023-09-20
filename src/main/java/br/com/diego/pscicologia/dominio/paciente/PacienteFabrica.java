package br.com.diego.pscicologia.dominio.paciente;

import br.com.diego.pscicologia.comum.ExcecaoDeRegraDeNegocio;
import br.com.diego.pscicologia.comum.Moeda;
import br.com.diego.pscicologia.comum.Quantidade;

import java.util.Objects;

public class PacienteFabrica {

    public PacienteFabrica() {
    }

    public Paciente fabricar(String nome,
                             String endereco,
                             Quantidade quantidaDeDiasNoMes,
                             Moeda valorPorSessao,
                             Tipo tipo) {

        validarTipoDePaciente(tipo);
        if (tipo.ehValorFixo()) {
            return new Paciente(nome, endereco, valorPorSessao);
        } else {
            return new Paciente(nome, endereco, quantidaDeDiasNoMes, valorPorSessao);
        }
    }

    private void validarTipoDePaciente(Tipo tipo) {
        if (Objects.isNull(tipo)) {
            throw new ExcecaoDeRegraDeNegocio("É necessário informar o tipo de paciente para criar um paciente.");
        }
    }
}
