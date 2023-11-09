package br.com.diego.psicologia.servico.autenticacao;

public interface AutenticadorDeUsuario {
    UsuarioAutenticadoDTO autenticar(String username, String password);
}
