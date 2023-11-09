package br.com.diego.psicologia.dominio.paciente;

import br.com.diego.psicologia.comum.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


//TODO: ver o melhor nome para compor esse objeto de valor
public class Valor {
    private Quantidade quantidadeDeDiasNoMes;
    private Moeda valorPorSessao;
    private Mes mes;
    private Integer ano;
    private TipoDePaciente tipoDePaciente;
    List<LocalDate> datasDasSessoes;


    public Valor(Moeda valorPorSessao, Mes mes, Integer ano, TipoDePaciente tipoDePaciente, List<LocalDate> datasDasSessoes) {
        validarCamposObrigatorios(valorPorSessao, mes, ano, tipoDePaciente);
        validarQuantidadeDeDiasNoMes((Objects.isNull(datasDasSessoes) || datasDasSessoes.isEmpty()), tipoDePaciente);
        this.quantidadeDeDiasNoMes = tipoDePaciente.ehValorPorSessao() ? Quantidade.criar(datasDasSessoes.size()) : null;
        this.valorPorSessao = valorPorSessao;
        this.mes = mes;
        this.ano = ano;
        this.tipoDePaciente = tipoDePaciente;
        this.datasDasSessoes = tipoDePaciente.ehValorPorSessao() ? datasDasSessoes : Collections.emptyList();
    }

    private void validarQuantidadeDeDiasNoMes(boolean naoPossuiQuantidadeDeDiasNoMes, TipoDePaciente tipoDePaciente) {
        if (tipoDePaciente.ehValorPorSessao() && naoPossuiQuantidadeDeDiasNoMes) {
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

    public List<LocalDate> getDatasDasSessoes() {
        return datasDasSessoes;
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

    public Moeda obterValorTotal() {
        if (tipoDePaciente.ehValorFixo()) {
            return getValorPorSessao();
        }
        return getValorPorSessao().multiplicar(getQuantidadeDeDiasNoMes());
    }
}
