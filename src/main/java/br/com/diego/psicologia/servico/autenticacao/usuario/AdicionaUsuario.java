package br.com.diego.psicologia.servico.autenticacao.usuario;

public interface AdicionaUsuario {
    String executar(String username, String password, String role);
}
