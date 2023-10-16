package br.com.diego.pscicologia.servico.autenticacaodeusuario;

public interface AutenticadorDeUsuario {
    String autenticar(String username, String password);
}
