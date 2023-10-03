package br.com.diego.pscicologia.servico.usuario;

import br.com.diego.pscicologia.comum.ServicoDeAplicacaoDeConsulta;
import br.com.diego.pscicologia.dominio.usuario.UsuarioRepositorio;
import org.springframework.stereotype.Service;

public interface ConsultaUsuario extends ServicoDeAplicacaoDeConsulta<UsuarioDTO, FiltroDeConsultaDeUsuario> {
}
