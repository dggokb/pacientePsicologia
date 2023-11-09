package br.com.diego.psicologia.comum;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class NumeroPorExtenso {
    private static final BigInteger THOUSAND = new BigInteger("1000");
    private static final BigInteger HUNDRED = new BigInteger("100");
    private static final String CENTO = "cento";
    private static final String CEM = "cem";
    private static final Map<Integer, String> grandezasPlural = new HashMap();
    private static final Map<Integer, String> grandezasSingular = new HashMap();
    private static final Map<Integer, String> nomesDosNumeros = new HashMap();
    private static final String MOEDA_SINGULAR = "real";
    private static final String MOEDA_PLURAL = "reais";
    private static final String FRACAO_SINGULAR = "centavo";
    private static final String FRACAO_PLURAL = "centavos";
    private static final String PARTICULA_ADITIVA = "e";
    private static final String PARTICULA_DESCRITIVA = "de";
    private static final BigDecimal MAX_SUPPORTED_VALUE = new BigDecimal("999999999999999999999999999.99");

    private NumeroPorExtenso() {
    }

    public static String escrever(BigDecimal amount) {
        pararExecutacaoQuandoForNulo(amount);
        BigDecimal valorOriginal = arredondarParaDuasCasasDecimais(amount);
        paraExecucaoQuandoOValorEhMaiorQueOLimite(valorOriginal);
        int limiteDoValor = 0;
        if (valorOriginal.compareTo(BigDecimal.ZERO) <= limiteDoValor) {
            return "";
        } else {
            Stack<Integer> decomposed = decompose(valorOriginal);
            int expoente = calcularExpoente(decomposed);
            StringBuilder numeroPorExtenso = new StringBuilder();
            int lastNonZeroExponent = -1;
            int limiteDoExpoente = -3;

            for (byte decrecimoDoExpoente = 3; !decomposed.empty(); expoente -= decrecimoDoExpoente) {
                int valorDecomposto = (Integer) decomposed.pop();
                if (valorDecomposto > limiteDoValor) {
                    numeroPorExtenso.append(" ").append("e").append(" ");
                    numeroPorExtenso.append(comporNomeGrupos(valorDecomposto));
                    String nomeGrandeza = obterNomeGrandeza(expoente, valorDecomposto);
                    adcionarEspacoQuandoNecessario(numeroPorExtenso, nomeGrandeza);
                    numeroPorExtenso.append(nomeGrandeza);
                    lastNonZeroExponent = expoente;
                }

                if (expoente == limiteDoValor) {
                    tratarTextoDaParteDeDezenaEMilhar(valorOriginal, numeroPorExtenso, lastNonZeroExponent);
                } else if (expoente == limiteDoExpoente) {
                    tratarTextoDaParteFracionaria(numeroPorExtenso, valorDecomposto);
                }
            }

            return numeroPorExtenso.substring(3);
        }
    }

    private static void adcionarEspacoQuandoNecessario(StringBuilder numeroPorExtenso, String nomeGrandeza) {
        if (nomeGrandeza.length() > 0) {
            numeroPorExtenso.append(" ");
        }

    }

    private static void tratarTextoDaParteDeDezenaEMilhar(BigDecimal value, StringBuilder numeroPorExtenso, int lastNonZeroExponent) {
        BigInteger parteInteira = value.toBigInteger();
        if (BigInteger.ONE.equals(parteInteira)) {
            numeroPorExtenso.append(" ").append("real");
        } else if (parteInteira.compareTo(BigInteger.ZERO) > 0) {
            adicionarTextoDescretivo(numeroPorExtenso, lastNonZeroExponent);
            numeroPorExtenso.append(" ").append("reais");
        }

    }

    private static void adicionarTextoDescretivo(StringBuilder numeroPorExtenso, int lastNonZeroExponent) {
        if (lastNonZeroExponent >= 6) {
            numeroPorExtenso.append(" ").append("de");
        }

    }

    private static void tratarTextoDaParteFracionaria(StringBuilder numeroPorExtenso, int valor) {
        if (1 == valor) {
            numeroPorExtenso.append(" ").append("centavo");
        } else if (valor > 1) {
            numeroPorExtenso.append(" ").append("centavos");
        }

    }

    private static int calcularExpoente(Stack<Integer> decomposed) {
        return 3 * (decomposed.size() - 2);
    }

    private static void paraExecucaoQuandoOValorEhMaiorQueOLimite(BigDecimal value) {
        if (MAX_SUPPORTED_VALUE.compareTo(value) < 0) {
            throw new IllegalArgumentException("Valor acima do limite suportado.");
        }
    }

    private static BigDecimal arredondarParaDuasCasasDecimais(BigDecimal amount) {
        int escala = 2;
        return amount.setScale(escala, 6);
    }

    private static void pararExecutacaoQuandoForNulo(BigDecimal amount) {
        if (null == amount) {
            throw new IllegalArgumentException();
        }
    }

    private static StringBuilder comporNomeGrupos(int valor) {
        StringBuilder nome = new StringBuilder();
        int valorCem = 100;
        int centenas = valor - valor % valorCem;
        int unidades = valor % 10;
        int dezenas = valor - centenas - unidades;
        int duasCasas = dezenas + unidades;
        int limiteParaDuasCasas = 20;
        int inicioDoLimite = 0;
        int fimDoLimite = 3;
        if (centenas > inicioDoLimite) {
            nome.append(" ").append("e").append(" ");
            if (valorCem == centenas) {
                if (duasCasas > inicioDoLimite) {
                    nome.append("cento");
                } else {
                    nome.append("cem");
                }
            } else {
                nome.append((String) nomesDosNumeros.get(centenas));
            }
        }

        if (duasCasas > inicioDoLimite) {
            nome.append(" ").append("e").append(" ");
            if (duasCasas < limiteParaDuasCasas) {
                nome.append((String) nomesDosNumeros.get(duasCasas));
            } else {
                if (dezenas > inicioDoLimite) {
                    nome.append((String) nomesDosNumeros.get(dezenas));
                }

                if (unidades > inicioDoLimite) {
                    nome.append(" ").append("e").append(" ");
                    nome.append((String) nomesDosNumeros.get(unidades));
                }
            }
        }

        return nome.delete(inicioDoLimite, fimDoLimite);
    }

    private static String obterNomeGrandeza(int exponent, int value) {
        int limiteDoExpoente = 3;
        if (exponent < limiteDoExpoente) {
            return "";
        } else {
            int valorSingular = 1;
            return valorSingular == value ? (String) grandezasSingular.get(exponent) : (String) grandezasPlural.get(exponent);
        }
    }

    private static Stack<Integer> decompose(BigDecimal value) {
        BigInteger intermediate = value.multiply(BigDecimal.valueOf(100L)).toBigInteger();
        Stack<Integer> decomposed = new Stack();
        BigInteger[] result = intermediate.divideAndRemainder(HUNDRED);
        intermediate = result[0];
        decomposed.add(result[1].intValue());

        while (intermediate.compareTo(BigInteger.ZERO) > 0) {
            result = intermediate.divideAndRemainder(THOUSAND);
            intermediate = result[0];
            decomposed.add(result[1].intValue());
        }

        if (decomposed.size() == 1) {
            decomposed.add(0);
        }

        return decomposed;
    }

    private static void preencherGrandezasPlural() {
        grandezasPlural.put(3, "mil");
        grandezasPlural.put(6, "milhões");
        grandezasPlural.put(9, "bilhões");
        grandezasPlural.put(12, "trilhões");
        grandezasPlural.put(15, "quatrilhões");
        grandezasPlural.put(18, "quintilhões");
        grandezasPlural.put(21, "sextilhões");
        grandezasPlural.put(24, "setilhões");
    }

    private static void preencherGrandezasSingular() {
        grandezasSingular.put(3, "mil");
        grandezasSingular.put(6, "milhão");
        grandezasSingular.put(9, "bilhão");
        grandezasSingular.put(12, "trilhão");
        grandezasSingular.put(15, "quatrilhão");
        grandezasSingular.put(18, "quintilhão");
        grandezasSingular.put(21, "sextilhão");
        grandezasSingular.put(24, "setilhão");
    }

    private static void preencherNomes() {
        nomesDosNumeros.put(1, "um");
        nomesDosNumeros.put(2, "dois");
        nomesDosNumeros.put(3, "três");
        nomesDosNumeros.put(4, "quatro");
        nomesDosNumeros.put(5, "cinco");
        nomesDosNumeros.put(6, "seis");
        nomesDosNumeros.put(7, "sete");
        nomesDosNumeros.put(8, "oito");
        nomesDosNumeros.put(9, "nove");
        nomesDosNumeros.put(10, "dez");
        nomesDosNumeros.put(11, "onze");
        nomesDosNumeros.put(12, "doze");
        nomesDosNumeros.put(13, "treze");
        nomesDosNumeros.put(14, "quatorze");
        nomesDosNumeros.put(15, "quinze");
        nomesDosNumeros.put(16, "dezesseis");
        nomesDosNumeros.put(17, "dezessete");
        nomesDosNumeros.put(18, "dezoito");
        nomesDosNumeros.put(19, "dezenove");
        nomesDosNumeros.put(20, "vinte");
        nomesDosNumeros.put(30, "trinta");
        nomesDosNumeros.put(40, "quarenta");
        nomesDosNumeros.put(50, "cinquenta");
        nomesDosNumeros.put(60, "sessenta");
        nomesDosNumeros.put(70, "setenta");
        nomesDosNumeros.put(80, "oitenta");
        nomesDosNumeros.put(90, "noventa");
        nomesDosNumeros.put(200, "duzentos");
        nomesDosNumeros.put(300, "trezentos");
        nomesDosNumeros.put(400, "quatrocentos");
        nomesDosNumeros.put(500, "quinhentos");
        nomesDosNumeros.put(600, "seiscentos");
        nomesDosNumeros.put(700, "setecentos");
        nomesDosNumeros.put(800, "oitocentos");
        nomesDosNumeros.put(900, "novecentos");
    }

    static {
        preencherGrandezasPlural();
        preencherGrandezasSingular();
        preencherNomes();
    }
}
