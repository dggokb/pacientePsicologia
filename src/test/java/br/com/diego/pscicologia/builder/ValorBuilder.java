package br.com.diego.pscicologia.builder;

import br.com.diego.pscicologia.comum.Mes;
import br.com.diego.pscicologia.comum.Moeda;
import br.com.diego.pscicologia.comum.Quantidade;
import br.com.diego.pscicologia.dominio.paciente.Tipo;
import br.com.diego.pscicologia.dominio.paciente.Valor;

import java.time.LocalDate;

public class ValorBuilder {
    private Quantidade quantidadeDeDiasNoMes;
    private Moeda valorPorSessao;
    private Mes mes;
    private Integer ano;
    private Tipo tipo;

    public ValorBuilder() {
        this.quantidadeDeDiasNoMes = Quantidade.criar(10);
        this.valorPorSessao = Moeda.criar(10);
        this.mes = Mes.JANEIRO;
        this.ano = LocalDate.now().getYear();
        this.tipo = Tipo.VALOR_POR_SESSAO;
    }

    public Valor criar() {
        return new Valor(quantidadeDeDiasNoMes, valorPorSessao, mes, ano, tipo);
    }

    public ValorBuilder comQuantidadeDeDiasNoMes(Quantidade quantidadeDeDiasNoMes) {
        this.quantidadeDeDiasNoMes = quantidadeDeDiasNoMes;
        return this;
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

    public ValorBuilder comTipo(Tipo tipo) {
        this.tipo = tipo;
        return this;
    }
}
