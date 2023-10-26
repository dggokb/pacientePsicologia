package br.com.diego.pscicologia.servico.autenticacao.usuario;

public interface AdicionaUsuario {
    String executar(String username, String password, String role);
}
