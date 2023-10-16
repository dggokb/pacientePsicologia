package br.com.diego.pscicologia.servico.autenticacaodeusuario.usuario;

public interface AdicionaUsuario {
    String executar(String username, String password, String role);
}
