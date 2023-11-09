package br.com.diego.psicologia.builder;

import br.com.diego.psicologia.comum.Mes;
import br.com.diego.psicologia.comum.Moeda;
import br.com.diego.psicologia.dominio.paciente.TipoDePaciente;
import br.com.diego.psicologia.dominio.paciente.Valor;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ValorBuilder {
    private Moeda valorPorSessao;
    private Mes mes;
    private Integer ano;
    private TipoDePaciente tipoDePaciente;
    private List<LocalDate> datasDasSessoes;

    public ValorBuilder() {
        this.valorPorSessao = Moeda.criar(10);
        this.mes = Mes.JANEIRO;
        this.ano = LocalDate.now().getYear();
        this.tipoDePaciente = TipoDePaciente.VALOR_POR_SESSAO;
        this.datasDasSessoes = Arrays.asList(LocalDate.now(), LocalDate.now().plusDays(1));
    }

    public Valor criar() {
        return new Valor(valorPorSessao, mes, ano, tipoDePaciente, datasDasSessoes);
    }

    public ValorBuilder comValorPorSessao(Moeda valorPorSessao) {
        this.valorPorSessao = valorPorSessao;
        return this;
    }

    public ValorBuilder comMes(Mes mes) {
        this.mes = mes;
        return this;
    }

    public ValorBuilder comAno(Integer ano) {
        this.ano = ano;
        return this;
    }

    public ValorBuilder comTipo(TipoDePaciente tipoDePaciente) {
        this.tipoDePaciente = tipoDePaciente;
        return this;
    }
}
