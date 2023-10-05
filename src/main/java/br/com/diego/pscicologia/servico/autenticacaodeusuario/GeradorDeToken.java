package br.com.diego.pscicologia.servico.autenticacaodeusuario;

import br.com.diego.pscicologia.dominio.usuario.Usuario;

public interface GeradorDeToken {
    String gerar(Usuario usuario);
}
