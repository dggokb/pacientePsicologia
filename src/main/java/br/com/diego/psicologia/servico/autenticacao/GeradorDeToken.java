package br.com.diego.psicologia.servico.autenticacao;

import br.com.diego.psicologia.dominio.usuario.Usuario;

public interface GeradorDeToken {
    UsuarioAutenticadoDTO gerar(Usuario usuario);
}
