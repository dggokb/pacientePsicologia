package br.com.diego.psicologia.comum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ExcecaoDeCampoObrigatorio extends ExcecaoDeRegraDeNegocio {
    private List<String> erros = new ArrayList();

    public ExcecaoDeCampoObrigatorio() {
        super("");
    }

    private ExcecaoDeCampoObrigatorio(String mensagem, List<String> erros) {
        super(mensagem);
        this.erros = erros;
    }

    public List<String> getErros() {
        return this.erros;
    }

    public ExcecaoDeCampoObrigatorio quandoNulo(Object obj, String mensagem) {
        this.quando(obj == null, mensagem);
        return this;
    }

    public ExcecaoDeCampoObrigatorio quandoColecaoNula(Collection colecao, String mensagem) {
        this.quando(colecao == null, mensagem);
        return this;
    }

    public ExcecaoDeCampoObrigatorio quandoColecaoVazia(Collection colecao, String mensagem) {
        this.quandoColecaoNula(colecao, mensagem);
        this.quando(colecao.isEmpty(), mensagem);
        return this;
    }

    public ExcecaoDeCampoObrigatorio quandoValorMenorOuIgualZero(BigDecimal valor, String mensagem) {
        this.quando(valor == null || valor.compareTo(BigDecimal.ZERO) <= 0, mensagem);
        return this;
    }

    public ExcecaoDeCampoObrigatorio quandoValorMenorOuIgualZero(Quantidade quantidade, String mensagem) {
        this.quando(quantidade == null || quantidade.valor() == null || quantidade.valor().compareTo(BigDecimal.ZERO) <= 0, mensagem);
        return this;
    }

    public ExcecaoDeCampoObrigatorio quandoValorMenorOuIgualZero(Integer valor, String mensagem) {
        this.quando(valor == null || valor <= 0, mensagem);
        return this;
    }

    public ExcecaoDeCampoObrigatorio quandoValorMenorOuIgualZero(int valor, String mensagem) {
        this.quando(valor <= 0, mensagem);
        return this;
    }

    public ExcecaoDeCampoObrigatorio quandoValorMenorOuIgualZero(Double valor, String mensagem) {
        this.quando(valor == null || valor <= 0.0D, mensagem);
        return this;
    }

    public ExcecaoDeCampoObrigatorio quandoValorMenorQueZero(Double valor, String mensagem) {
        this.quando(valor == null || valor < 0.0D, mensagem);
        return this;
    }

    public ExcecaoDeCampoObrigatorio quandoValorMenorQueZero(BigDecimal valor, String mensagem) {
        this.quando(valor == null || valor.compareTo(BigDecimal.ZERO) < 0, mensagem);
        return this;
    }

    public ExcecaoDeCampoObrigatorio quandoValorMenorQueZero(Moeda valor, String mensagem) {
        this.quando(valor == null || valor.ehMenor(Moeda.ZERO), mensagem);
        return this;
    }

    public ExcecaoDeCampoObrigatorio quandoValorMenorQueZero(Integer valor, String mensagem) {
        this.quando(valor == null || valor < 0, mensagem);
        return this;
    }

    public ExcecaoDeCampoObrigatorio quandoVazio(String valor, String mensagem) {
        this.quando(valor == null || valor.isEmpty(), mensagem);
        return this;
    }

    public ExcecaoDeCampoObrigatorio quandoValorMenorOuIgualZero(Moeda valor, String mensagem) {
        this.quando(valor == null || valor.ehMenor(Moeda.ZERO) || valor.ehIgual(Moeda.ZERO), mensagem);
        return this;
    }

    public ExcecaoDeCampoObrigatorio quandoValorIgualAZero(Quantidade quantidade, String mensagem) {
        this.quando(quantidade == null || Quantidade.ZERO.ehIgual(quantidade), mensagem);
        return this;
    }

    public ExcecaoDeCampoObrigatorio quando(boolean condicao, String mensagem) {
        if (condicao) {
            this.inserir(mensagem);
        }

        return this;
    }

    private void inserir(String msg) {
        this.erros.add(msg);
    }

    public void entaoDispara() {
        if (!this.erros.isEmpty()) {
            String mensagem = this.construirMensagem();
            throw new ExcecaoDeCampoObrigatorio(mensagem, this.erros);
        }
    }

    private String construirMensagem() {
        StringBuilder mensagem = new StringBuilder();
        Iterator var2 = this.erros.iterator();

        while(var2.hasNext()) {
            String erro = (String)var2.next();
            mensagem.append(erro + "\n");
        }

        return mensagem.toString();
    }
}
