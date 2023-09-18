package br.com.diego.pscicologia.comum;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;


public class Moeda {
    private BigDecimal valor;
    private int dizima;
    private RoundingMode modoDeArredondamento;
    public static final Moeda ZERO;

    private Moeda() {
        this.valor = BigDecimal.ZERO;
        this.dizima = 2;
        this.modoDeArredondamento = RoundingMode.HALF_EVEN;
    }

    private Moeda(BigDecimal valor, int dizima, RoundingMode roundingMode) {
        this.valor = BigDecimal.ZERO;
        this.dizima = 2;
        this.modoDeArredondamento = RoundingMode.HALF_EVEN;
        this.setValor(valor);
        this.setDizima(dizima);
        this.setModoDeArredondamento(roundingMode);
    }

    public static Moeda criar() {
        return criar(BigDecimal.ZERO);
    }

    public static Moeda criar(BigDecimal valor) {
        valor = verificarSeValorEhNulo(valor);
        BigDecimal bigDecimal = new BigDecimal(valor.doubleValue(), MathContext.DECIMAL128);
        return new Moeda(bigDecimal, 2, RoundingMode.HALF_EVEN);
    }

    public static Moeda criar(BigDecimal valor, int dizima) {
        valor = verificarSeValorEhNulo(valor);
        return new Moeda(valor, dizima, RoundingMode.HALF_EVEN);
    }

    public static Moeda criar(int valor) {
        BigDecimal novoValor = verificarSeValorEhNulo(new BigDecimal(valor, MathContext.DECIMAL128));
        return criar(novoValor);
    }

    public static Moeda criar(double valor) {
        BigDecimal novoValor = verificarSeValorEhNulo(new BigDecimal(valor, MathContext.DECIMAL128));
        return criar(novoValor);
    }

    private static BigDecimal verificarSeValorEhNulo(BigDecimal valor) {
        return valor == null ? BigDecimal.ZERO : valor;
    }

    public BigDecimal valor() {
        return this.getValor();
    }

    public String valorFormatado() {
        return NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(this.valor());
    }

    public String valorPorExtenso() {
        return NumeroPorExtenso.escrever(this.valor());
    }

    public String valorFormatadoEPorExtenso() {
        return this.valorFormatado().concat(String.format(" (%s)", this.valorPorExtenso()));
    }

    public BigDecimal valorArredondado() {
        return this.getValor().setScale(this.dizima, this.modoDeArredondamento);
    }

    public Moeda somar(Moeda valorQueSeraSomado) {
        return this.somar(valorQueSeraSomado.valor());
    }

    public Moeda somar(BigDecimal valor) {
        return criar(this.valor.add(valor, MathContext.DECIMAL128));
    }

    public Moeda subtrair(Moeda valor) {
        return this.subtrair(valor.valor());
    }

    public Moeda subtrair(BigDecimal valor) {
        return criar(this.valor.subtract(valor));
    }

    public Moeda multiplicar(Moeda valor) {
        return this.multiplicar(valor.valor());
    }

    public Moeda multiplicar(Quantidade quantidade) {
        return this.multiplicar(quantidade.valorSemArredondamento());
    }

    public Moeda multiplicar(BigDecimal valor) {
        return criar(this.valor.multiply(valor, MathContext.DECIMAL128));
    }

    public Moeda dividir(Integer valor) {
        return this.dividir(criar(valor));
    }

    public Moeda dividir(Moeda valor) {
        return this.dividir(valor.valor());
    }

    public Moeda dividir(BigDecimal valor) {
        return criar(this.valor.divide(valor, MathContext.DECIMAL128));
    }

    public boolean ehMaior(Moeda valor) {
        return BigDecimalUtils.ehMaiorQue(this.valor(), valor.valor());
    }

    public boolean ehMenor(Moeda valor) {
        return BigDecimalUtils.ehMenor(this.valor(), valor.valor());
    }

    public boolean ehMenorOuIgualAZero() {
        return this.ehMenor(ZERO) || this.ehZero();
    }

    public boolean ehIgual(Moeda valor) {
        return BigDecimalUtils.ehIgual(this.valor, valor.valor());
    }

    public boolean ehZero() {
        return BigDecimalUtils.ehIgual(this.valor(), BigDecimal.ZERO);
    }

    private void setDizima(int dizima) {
        this.dizima = dizima;
    }

    private void setModoDeArredondamento(RoundingMode modoDeArredondamento) {
        this.modoDeArredondamento = modoDeArredondamento;
    }

    private BigDecimal getValor() {
        return this.valor;
    }

    private void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.valor});
    }

    public boolean equals(Object object) {
        if (object instanceof Moeda) {
            Moeda other = (Moeda) object;
            return BigDecimalUtils.ehIgual(this.valor(), other.valor());
        } else {
            return false;
        }
    }

    public String toString() {
        return "Moeda{valor=" + this.valor + ", dizima=" + this.dizima + ", modoDeArredondamento=" + this.modoDeArredondamento + '}';
    }

    static {
        ZERO = new Moeda(BigDecimal.ZERO, 2, RoundingMode.HALF_EVEN);
    }
}
