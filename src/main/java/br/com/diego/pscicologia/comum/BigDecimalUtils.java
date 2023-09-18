package br.com.diego.pscicologia.comum;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Comparator;
import java.util.Locale;

public class BigDecimalUtils {
    private static final int DUAS_CASAS_DECIMAIS = 2;
    private static final Locale LOCAL_BRASIL = new Locale("pt", "BR");
    private static final Double ZERO = 0.0D;

    private BigDecimalUtils() {
    }

    public static Double arredondarParaCima(Double valorQuebrado) {
        if (valorQuebrado == null) {
            return ZERO;
        } else {
            BigDecimal valorASerArredondado = arredondarParaCima(BigDecimal.valueOf(valorQuebrado));
            return valorASerArredondado.doubleValue();
        }
    }

    public static BigDecimal arredondarParaCimaEConverterParaBigDecimal(Double valorQuebrado) {
        return BigDecimal.valueOf(arredondarParaCima(valorQuebrado));
    }

    public static BigDecimal arredondarParaCima(BigDecimal valorQuebrado) {
        return valorQuebrado.setScale(2, RoundingMode.HALF_UP);
    }

    public static Double dividirEArredondarParaCima(BigDecimal dividendo, BigDecimal divisor) {
        return dividendo.divide(divisor, 2, RoundingMode.HALF_UP).doubleValue();
    }

    public static Double limitarCasasDecimaisDeUmDouble(String formato, Double numeroASerFormatado) {
        DecimalFormat decimalFormat = new DecimalFormat(formato);
        String valorFormatadoTexto = decimalFormat.format(numeroASerFormatado);
        valorFormatadoTexto = valorFormatadoTexto.replace(',', '.');
        return Double.parseDouble(valorFormatadoTexto);
    }

    public static BigDecimal limitarCasasDecimais(int quantidadeDeCasasDecimais, BigDecimal valorASerLimitado) {
        return valorASerLimitado.setScale(quantidadeDeCasasDecimais, RoundingMode.HALF_UP);
    }

    public static boolean ehMaiorQue(BigDecimal valor1, BigDecimal valor2) {
        return (new BigDecimalUtils.BigDecimalComparator()).compare(valor1, valor2) > 0;
    }

    public static boolean ehIgual(BigDecimal valor1, BigDecimal valor2) {
        return (new BigDecimalUtils.BigDecimalComparator()).compare(valor1, valor2) == 0;
    }

    public static boolean ehMenor(BigDecimal valor1, BigDecimal valor2) {
        return (new BigDecimalUtils.BigDecimalComparator()).compare(valor1, valor2) < 0;
    }

    public static boolean ehMaiorOuIgual(BigDecimal valor1, BigDecimal valor2) {
        return (new BigDecimalUtils.BigDecimalComparator()).compare(valor1, valor2) >= 0;
    }

    private static class BigDecimalComparator implements Comparator<BigDecimal> {
        private BigDecimalComparator() {
        }

        public int compare(BigDecimal valor1, BigDecimal valor2) {
            int precisaoDoValor1 = valor1.precision();
            int precisaoDoValor2 = valor2.precision();
            BigDecimal valor2ArrendondadoParaMesmaPrecisao;
            if (precisaoDoValor1 > precisaoDoValor2) {
                valor2ArrendondadoParaMesmaPrecisao = valor1.round(new MathContext(precisaoDoValor2, RoundingMode.HALF_EVEN));
                return valor2ArrendondadoParaMesmaPrecisao.compareTo(valor2);
            } else if (precisaoDoValor2 > precisaoDoValor1) {
                valor2ArrendondadoParaMesmaPrecisao = valor2.round(new MathContext(precisaoDoValor1, RoundingMode.HALF_EVEN));
                return valor1.compareTo(valor2ArrendondadoParaMesmaPrecisao);
            } else {
                return valor1.compareTo(valor2);
            }
        }
    }
}

