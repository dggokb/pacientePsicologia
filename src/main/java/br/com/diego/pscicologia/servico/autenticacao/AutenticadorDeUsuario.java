package br.com.diego.pscicologia.servico.autenticacao;

public interface AutenticadorDeUsuario {
    String autenticar(String username, String password);
}
