package br.com.diego.pscicologia.dominio.paciente;

import br.com.diego.pscicologia.comum.*;

import java.util.Objects;

public class Valor {
    private Quantidade quantidadeDeDiasNoMes;
    private Moeda valorPorSessao;
    private Mes mes;
    private Integer ano;


    public Valor(Quantidade quantidadeDeDiasNoMes, Moeda valorPorSessao, Mes mes, Integer ano, Tipo tipo) {
        validarCamposObrigatorios(valorPorSessao, mes, ano, tipo);
        validarQuantidadeDeDiasNoMes(quantidadeDeDiasNoMes, tipo);
        this.quantidadeDeDiasNoMes = quantidadeDeDiasNoMes;
        this.valorPorSessao = valorPorSessao;
        this.mes = mes;
        this.ano = ano;
    }

    private void validarQuantidadeDeDiasNoMes(Quantidade quantidadeDeDiasNoMes, Tipo tipo) {
        if (tipo.ehValorPorSessao() && Objects.isNull(quantidadeDeDiasNoMes)) {
            throw new ExcecaoDeRegraDeNegocio("Não pode ser inserido um valor sem quantidade de dias no mês quando for valor por sessão.");
        }
    }

    private void validarCamposObrigatorios(Moeda valorPorSessao, Mes mes, Integer ano, Tipo tipo) {
        new ExcecaoDeCampoObrigatorio()
                .quandoNulo(valorPorSessao, "Não é possível criar um valor sem informar o valor por sessão.")
                .quandoNulo(mes, "Não é possível criar um valor sem informar o mês.")
                .quandoNulo(ano, "Não é possível criar um valor sem informar o ano.")
                .quandoNulo(tipo, "Não é possível criar um valor sem informar o tipo.")
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

    //testar
    public boolean ehDoMesmoMesEAno(Mes mes, Integer ano) {
        return ehDoMesmoAno(ano) && ehDoMesmoMes(mes);
    }

    private boolean ehDoMesmoAno(Integer ano) {
        return getAno().equals(ano);
    }


    private boolean ehDoMesmoMes(Mes mes) {
        return getMes().equals(mes);
    }
}
