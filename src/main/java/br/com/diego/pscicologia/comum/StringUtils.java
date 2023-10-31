package br.com.diego.pscicologia.comum;

import java.text.Normalizer;
import java.text.ParseException;
import java.util.Base64;
import java.util.Objects;
import java.util.stream.Stream;

public class StringUtils {

    private static final String FORMATADO_DO_TELEFONE_COM_OITO_DIGITOS = "(XX) XXXX-XXXX";
    private static final String FORMATO_DO_TELEFONE_COM_NOVE_DIGITOS = "(XX) XXXXX-XXXX";
    private static final Integer TAMANHO_MAXIMO_DO_TELEFONE = 11;

    public static String retirarAcentos(String texto) {
        return Normalizer
                .normalize(Objects.isNull(texto) ? "" : texto, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }

    public static String retirarCaracteresEspeciais(String texto) {
        texto = texto.replaceAll("[^a-zA-Z0-9]", "");
        return texto;
    }

    public static String retirarAcentosECaracteresEspeciais(String texto) {
        texto = retirarAcentos(texto);
        texto = retirarCaracteresEspeciais(texto);
        return texto;
    }

    public static boolean verificarSePossuiAcentosOuCaracteresEspeciais(String texto) {
        return !retirarAcentos(texto).equals(texto);
    }

    public static String removerNumerosDaString(String texto) {
        return texto.replaceAll("[^\\D+]", "");
    }

    public static String formartarValorNumerico(Double valorNumerico) {
        return !numeroQuebrado(valorNumerico) ? String.valueOf(valorNumerico.intValue()) : valorNumerico.toString().replace(".", ",");
    }

    private static boolean numeroQuebrado(Double valor) {
        return valor != valor.intValue();
    }

    public static boolean contemDesconsiderandoMaiusculasEMinusculasAcentosECaracteresEspeciais(String texto, String filtro) {
        String filtroSemCaracterEspecial = StringUtils.retirarAcentosECaracteresEspeciais(filtro.toLowerCase());
        String textoSemCaracterEspecial = StringUtils.retirarAcentosECaracteresEspeciais(texto.toLowerCase());
        return Stream.of(filtroSemCaracterEspecial.trim().split(" "))
                .anyMatch(textoSemCaracterEspecial::contains);
    }

    public static boolean igualDesconsiderandoMaiusculaMinusculaAcentosECaracteresEspeciais(String texto1, String texto2) {
        return StringUtils.retirarAcentosECaracteresEspeciais(texto1.toLowerCase()).equals(StringUtils.retirarAcentosECaracteresEspeciais(texto2.toLowerCase()));
    }

    public static byte[] decodificarEmBase64(String valor) {
        return Base64.getDecoder().decode(valor);
    }

    public static String codificarEmBase64(byte[] valor) {
        return Base64.getEncoder().encodeToString(valor);
    }


    public static String formatarTelefone(String telefone) throws ParseException {
        return telefone.length() == 10 ? aplicarMascaraNoNumeroDoTelefone(telefone, FORMATADO_DO_TELEFONE_COM_OITO_DIGITOS) : aplicarMascaraNoNumeroDoTelefone(telefone, FORMATO_DO_TELEFONE_COM_NOVE_DIGITOS);
    }

    public static String removerQuebrasDeLinha(String texto) {
        if (texto == null) {
            return null;
        }

        return texto.replaceAll("\r|\n", " ");
    }

    public static String removerTabs(String texto) {
        return texto.replace("\t", "");
    }

    private static String aplicarMascaraNoNumeroDoTelefone(String numeroTelefonico, String formatoDoTelefone) throws ParseException {
        String numeroPreparadoParaFormatar = prepararNumeroTelefonicoParaFormatar(numeroTelefonico);
        char[] telefoneFormatado = new char[formatoDoTelefone.length()];
        int posicaoDoNumeroDoTelefone = 0;
        for (int posicaoDoVetor = 0; posicaoDoVetor < formatoDoTelefone.length(); posicaoDoVetor++) {
            if (posicaoDoNumeroDoTelefone < numeroPreparadoParaFormatar.length()) {
                if (formatoDoTelefone.charAt(posicaoDoVetor) == 'X') {
                    telefoneFormatado[posicaoDoVetor] = numeroPreparadoParaFormatar.charAt(posicaoDoNumeroDoTelefone++);
                } else {
                    telefoneFormatado[posicaoDoVetor] = formatoDoTelefone.charAt(posicaoDoVetor);
                }
            }
        }
        return new String(telefoneFormatado);
    }

    private static String prepararNumeroTelefonicoParaFormatar(String numeroTelefonico) {
        String numeroPreparadoParaFormatar = numeroTelefonico.replaceAll("\\D+", "");
        if (numeroPreparadoParaFormatar.length() > TAMANHO_MAXIMO_DO_TELEFONE) {
            numeroPreparadoParaFormatar = numeroPreparadoParaFormatar.substring(0, TAMANHO_MAXIMO_DO_TELEFONE);
        }
        return numeroPreparadoParaFormatar;
    }

    public static boolean contemDesconsiderandoEspacosEQuebrasDeLinha(String texto, String substringContida) {
        String textoSemEspacosEQuebrasDeLinha = texto.replace(" ", "").replace("\n", "");
        String substringContidaSemEspacosEQuebrasDeLinha = substringContida.replace(" ", "").replace("\n", "");
        return textoSemEspacosEQuebrasDeLinha.contains(substringContidaSemEspacosEQuebrasDeLinha);
    }

    public static String removerEscapes(String texto) {
        return texto.replaceAll("\\s", "");
    }

    public static String removerEspacos(String conteudo) {
        return conteudo.replaceAll(" ", "");
    }

    public static String removerPontos(String conteudo){
        return conteudo.replace("." , "");
    }

    public static String removeEspacosAcentosCaracteresEspeciaisEPonto(String conteudo){
        String conteudoSemAcentosECaracteresEspeciais = retirarAcentosECaracteresEspeciais(conteudo);
        String conteudoSemEspacosAcentosECaracteresEspeciais = removerEspacos(conteudoSemAcentosECaracteresEspeciais);
        return removerPontos(conteudoSemEspacosAcentosECaracteresEspeciais);
    }

    public static boolean contemLetras(String conteudo) {
        String conteudoSemEspacosAcentosCaracteresEspeciaisEPonto = removeEspacosAcentosCaracteresEspeciaisEPonto(conteudo);
        return !conteudoSemEspacosAcentosCaracteresEspeciaisEPonto.matches("[0-9]+");
    }

    public static boolean contemApenasNumerosEOsCaracteresEspeciaisPermitidos(String conteudo, char... caracteresEspeciaisPermitidos) {
        String conteudoParaVerificar = Objects.nonNull(conteudo) ? conteudo : "";
        String conteudoComOsCaracteresRemovidos = removerApenasOsCaracteresInformados(conteudoParaVerificar, caracteresEspeciaisPermitidos);
        return !conteudoComOsCaracteresRemovidos.isEmpty() && contemApenasNumeros(conteudoComOsCaracteresRemovidos);
    }

    public static String removerApenasOsCaracteresInformados(String conteudo, char... caracteresParaRemover){
        for (char caracter : caracteresParaRemover) {
            String caracterParaRemover = String.valueOf(caracter);
            if(conteudo.contains(caracterParaRemover)) {
                conteudo = conteudo.replace(caracterParaRemover, "");
            }
        }
        return conteudo;
    }

    public static boolean contemApenasNumeros(String conteudo) {
        return conteudo.matches("[0-9]+");
    }
}
