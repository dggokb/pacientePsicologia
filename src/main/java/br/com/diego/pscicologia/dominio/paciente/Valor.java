package br.com.diego.pscicologia.dominio.paciente;

import br.com.diego.pscicologia.comum.ExcecaoDeCampoObrigatorio;
import br.com.diego.pscicologia.comum.Mes;
import br.com.diego.pscicologia.comum.Moeda;
import br.com.diego.pscicologia.comum.Quantidade;

public class Valor {
    private Quantidade quantidadeDeDiasNoMes;
    private Moeda valorPorSessao;
    private Mes mes;
    private Integer ano;


    public Valor(Quantidade quantidadeDeDiasNoMes, Moeda valorPorSessao, Mes mes, Integer ano) {
        validarCamposObrigatorios(valorPorSessao, mes, ano);
        this.quantidadeDeDiasNoMes = quantidadeDeDiasNoMes;
        this.valorPorSessao = valorPorSessao;
        this.mes = mes;
        this.ano = ano;
    }

    private void validarCamposObrigatorios(Moeda valorPorSessao, Mes mes, Integer ano) {
        new ExcecaoDeCampoObrigatorio()
                .quandoNulo(valorPorSessao, "Não é possível criar um valor sem informar o valor por sessão.")
                .quandoNulo(mes, "Não é possível criar um valor sem informar o mês.")
                .quandoNulo(ano, "Não é possível criar um valor sem informar o ano.")
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
}
