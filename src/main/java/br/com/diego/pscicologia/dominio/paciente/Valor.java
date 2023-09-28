package br.com.diego.pscicologia.dominio.paciente;

import br.com.diego.pscicologia.comum.*;

import java.util.Objects;


//TODO: ver o melhor nome para compor esse objeto de valor
public class Valor {
    private Quantidade quantidadeDeDiasNoMes;
    private Moeda valorPorSessao;
    private Mes mes;
    private Integer ano;
    private TipoDePaciente tipoDePaciente;


    public Valor(Quantidade quantidadeDeDiasNoMes, Moeda valorPorSessao, Mes mes, Integer ano, TipoDePaciente tipoDePaciente) {
        validarCamposObrigatorios(valorPorSessao, mes, ano, tipoDePaciente);
        validarQuantidadeDeDiasNoMes(quantidadeDeDiasNoMes, tipoDePaciente);
        this.quantidadeDeDiasNoMes = !tipoDePaciente.ehValorFixo() ? quantidadeDeDiasNoMes : null;
        this.valorPorSessao = valorPorSessao;
        this.mes = mes;
        this.ano = ano;
        this.tipoDePaciente = tipoDePaciente;
    }

    private void validarQuantidadeDeDiasNoMes(Quantidade quantidadeDeDiasNoMes, TipoDePaciente tipoDePaciente) {
        if (tipoDePaciente.ehValorPorSessao() && Objects.isNull(quantidadeDeDiasNoMes)) {
            throw new ExcecaoDeRegraDeNegocio("Não pode ser inserido um valor sem quantidade de dias no mês quando for valor por sessão.");
        }
    }

    private void validarCamposObrigatorios(Moeda valorPorSessao, Mes mes, Integer ano, TipoDePaciente tipoDePaciente) {
        new ExcecaoDeCampoObrigatorio()
                .quandoNulo(valorPorSessao, "Não é possível criar um valor sem informar o valor por sessão.")
                .quandoNulo(mes, "Não é possível criar um valor sem informar o mês.")
                .quandoNulo(ano, "Não é possível criar um valor sem informar o ano.")
                .quandoNulo(tipoDePaciente, "Não é possível criar um valor sem informar o tipo.")
                .entaoDispara();
    }

    public Quantidade getQuantidadeDeDiasNoMes() {
        return quantidadeDeDiasNoMes;
    }

    public Moeda getValorPorSessao() {
        return valorPorSessao;
    }

    public Mes getMes() {
        return mes;
    }

    public Integer getAno() {
        return ano;
    }

    public boolean ehDoMesmo(Mes mes, Integer ano) {
        return ehDoMesmo(ano) && ehDoMesmo(mes);
    }

    private boolean ehDoMesmo(Integer ano) {
        return getAno().equals(ano);
    }


    private boolean ehDoMesmo(Mes mes) {
        return getMes().equals(mes);
    }
}
