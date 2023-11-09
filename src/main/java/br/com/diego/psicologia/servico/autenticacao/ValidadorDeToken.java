package br.com.diego.psicologia.servico.autenticacao;

public interface ValidadorDeToken {
    String validar(String token);
}
