package br.com.diego.pscicologia.comum;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Objects;

public class Quantidade {
    private BigDecimal valor;
    private static final Integer ESCALA_PADRAO = 2;
    private static final RoundingMode MODO_DE_ARREDONDAMENTO_PADRAO;
    private Integer escala;
    private RoundingMode modoDeArredondamento;
    public static final Quantidade ZERO;

    private Quantidade() {
        this.escala = ESCALA_PADRAO;
        this.modoDeArredondamento = MODO_DE_ARREDONDAMENTO_PADRAO;
    }

    private Quantidade(BigDecimal valor, Integer escala, RoundingMode modoDeArredondamento) {
        this.escala = ESCALA_PADRAO;
        this.modoDeArredondamento = MODO_DE_ARREDONDAMENTO_PADRAO;
        this.setValor(valor);
        this.escala = escala;
        this.modoDeArredondamento = modoDeArredondamento;
    }

    public static Quantidade criar(BigDecimal valor, Integer escala, RoundingMode modoDeArredondamento) {
        (new ExcecaoDeCampoObrigatorio()).quandoNulo(valor, "O campo valor é obrigatório.").entaoDispara();
        BigDecimal bigDecimal = new BigDecimal(valor.doubleValue(), MathContext.DECIMAL128);
        return new Quantidade(bigDecimal, escala, modoDeArredondamento);
    }

    public static Quantidade criar(BigDecimal valor) {
        return criar(valor, ESCALA_PADRAO, MODO_DE_ARREDONDAMENTO_PADRAO);
    }

    public static Quantidade criar(BigDecimal valor, Integer escala) {
        return criar(valor, escala, MODO_DE_ARREDONDAMENTO_PADRAO);
    }

    public static Quantidade criar(BigDecimal valor, RoundingMode modoDeArredondamento) {
        return criar(valor, ESCALA_PADRAO, modoDeArredondamento);
    }

    public static Quantidade criar(double valor) {
        return criar(BigDecimal.valueOf(valor));
    }

    public static Quantidade criar(double valor, int escala) {
        return criar(BigDecimal.valueOf(valor), escala);
    }

    public static Quantidade criar(double valor, RoundingMode modoDeArredondamento) {
        return criar(BigDecimal.valueOf(valor), modoDeArredondamento);
    }

    public static Quantidade criar(double valor, int escala, RoundingMode modoDeArredondamento) {
        return criar(BigDecimal.valueOf(valor), escala, modoDeArredondamento);
    }

    public Quantidade subtrair(Quantidade quantidade) {
        BigDecimal subtracao = this.valor.subtract(quantidade.valor, MathContext.DECIMAL128);
        return criar(subtracao, this.escala, this.modoDeArredondamento);
    }

    public Quantidade somar(Quantidade quantidade) {
        BigDecimal adicao = this.valor.add(quantidade.valor, MathContext.DECIMAL128);
        return criar(adicao, this.escala, this.modoDeArredondamento);
    }

    public Quantidade dividir(Quantidade quantidade) {
        BigDecimal resultado = this.valor.divide(quantidade.valor, MathContext.DECIMAL128);
        return criar(resultado, this.escala, this.modoDeArredondamento);
    }

    public Quantidade multiplicar(Quantidade quantidade) {
        BigDecimal multiplicacao = this.valor.multiply(quantidade.valor, MathContext.DECIMAL128);
        return criar(multiplicacao, this.escala, this.modoDeArredondamento);
    }

    public boolean ehIgual(Quantidade quantidade) {
        return this.valor().compareTo(quantidade.valor()) == 0;
    }

    public boolean ehIgualZero() {
        return this.ehIgual(ZERO);
    }

    public boolean ehMaior(Quantidade quantidade) {
        return this.valor().compareTo(quantidade.valor()) > 0;
    }

    public boolean ehMaiorQueZero() {
        return this.ehMaior(ZERO);
    }

    public boolean ehMenor(Quantidade quantidade) {
        return this.valor().compareTo(quantidade.valor()) < 0;
    }

    public boolean ehMenorQueZero() {
        return this.ehMenor(ZERO);
    }

    public boolean ehMenorOuIgualAZero() {
        return this.ehMenor(ZERO) || this.ehIgualZero();
    }

    public BigDecimal valor() {
        return this.getValor();
    }

    public BigDecimal valorArredondado() {
        return this.getValor().setScale(this.escala, this.modoDeArredondamento);
    }

    public BigDecimal valorSemArredondamento() {
        return this.valor;
    }

    private BigDecimal getValor() {
        return this.valor.setScale(this.escala, this.modoDeArredondamento);
    }

    private void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Quantidade) {
            Quantidade quantidade = (Quantidade)obj;
            return Objects.equals(this.valor(), quantidade.valor());
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.valor});
    }

    public String toString() {
        return this.valor.toString();
    }

    static {
        MODO_DE_ARREDONDAMENTO_PADRAO = RoundingMode.HALF_EVEN;
        ZERO = new Quantidade(BigDecimal.ZERO, ESCALA_PADRAO, MODO_DE_ARREDONDAMENTO_PADRAO);
    }
}

