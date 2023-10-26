package br.com.diego.pscicologia.servico.autenticacao.usuario;

import br.com.diego.pscicologia.dominio.usuario.TipoDeUsuario;
import br.com.diego.pscicologia.dominio.usuario.Usuario;
import br.com.diego.pscicologia.dominio.usuario.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdicionaUsuarioConcreto implements AdicionaUsuario {
    private final UsuarioRepositorio usuarioRepositorio;

    @Autowired
    public AdicionaUsuarioConcreto(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public String executar(String username, String password, String role) {
        validarUsuarioASerAdicionado(username);
        String encryptedPassword = new BCryptPasswordEncoder().encode(password);
        Usuario usuario = new Usuario(username, encryptedPassword, TipoDeUsuario.valueOf(role));
        usuarioRepositorio.save(usuario);

        return usuario.getId();
    }

    private void validarUsuarioASerAdicionado(String username) {
        usuarioRepositorio.finByUsername(username).ifPresent(details -> {
            throw new RuntimeException(String.format("Usuário %s já cadastrado.", username));
        });
    }
}
