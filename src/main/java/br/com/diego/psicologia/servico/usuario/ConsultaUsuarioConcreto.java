package br.com.diego.psicologia.servico.usuario;

import br.com.diego.psicologia.dominio.usuario.Usuario;
import br.com.diego.psicologia.dominio.usuario.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsultaUsuarioConcreto implements ConsultaUsuario {

    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    public ConsultaUsuarioConcreto(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public UsuarioDTO consultar(FiltroDeConsultaDeUsuario filtro) throws Exception {
        Usuario usuario = usuarioRepositorio.findById(filtro.getId())
                .orElseThrow(() -> new Exception("Não foi possível contrar o usuário"));

        return criarDTO(usuario);
    }

    private static UsuarioDTO criarDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.id = usuario.getId();
        dto.username = usuario.getUsername();
        dto.password = usuario.getPassword();
        return dto;
    }
}
