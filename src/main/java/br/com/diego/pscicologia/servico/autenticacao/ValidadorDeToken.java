package br.com.diego.pscicologia.servico.autenticacao;

public interface ValidadorDeToken {
    String validar(String token);
}
