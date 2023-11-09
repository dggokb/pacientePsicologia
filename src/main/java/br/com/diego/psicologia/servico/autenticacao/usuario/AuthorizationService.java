package br.com.diego.psicologia.servico.autenticacao.usuario;

import br.com.diego.psicologia.dominio.usuario.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    private final UsuarioRepositorio usuarioRepositorio;

    @Autowired
    public AuthorizationService(UsuarioRepositorio usuarioRepositorio) {
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return usuarioRepositorio.finByUsername(username).orElseThrow(() -> new Exception("Não foi possível obter o usuário"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
