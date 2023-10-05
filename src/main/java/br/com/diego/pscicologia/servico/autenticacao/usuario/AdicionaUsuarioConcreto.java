package br.com.diego.pscicologia.servico.autenticacao.usuario;

import br.com.diego.pscicologia.dominio.usuario.UserRole;
import br.com.diego.pscicologia.dominio.usuario.Usuario;
import br.com.diego.pscicologia.dominio.usuario.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdicionaUsuarioConcreto implements AdicionaUsuario {
    private final UsuarioRepositorio usuarioRepositorio;

    @Autowired
    public AdicionaUsuarioConcreto(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public String executar(String username, String password, String role) {
        Optional<UserDetails> userDetails = usuarioRepositorio.finByUsername(username);
//        if (userDetails.isPresent()) {
//            return ResponseEntity.badRequest().build();
//        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(password);
        Usuario usuario = new Usuario(username, encryptedPassword, UserRole.valueOf(role));
        usuarioRepositorio.save(usuario);

        return usuario.getId();
    }
}
