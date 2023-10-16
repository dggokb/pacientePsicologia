package br.com.diego.pscicologia.servico.autenticacaodeusuario;

public interface ValidadorDeToken {
    String validar(String token);
}
