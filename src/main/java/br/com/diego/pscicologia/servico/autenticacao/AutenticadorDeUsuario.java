package br.com.diego.pscicologia.servico.autenticacao;

public interface AutenticadorDeUsuario {
    UsuarioAutenticadoDTO autenticar(String username, String password);
}
