package br.com.diego.pscicologia.servico.autenticacao;

import br.com.diego.pscicologia.dominio.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AutenticaUsuarioConcreto implements AutenticadorDeUsuario {

    private final AuthenticationManager authenticationManager;
    private final GeradorDeToken generateToken;

    @Autowired
    public AutenticaUsuarioConcreto(AuthenticationManager authenticationManager, GeradorDeToken generateToken) {
        this.authenticationManager = authenticationManager;
        this.generateToken = generateToken;
    }

    @Override
    public String autenticar(String username, String password) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication auth = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        return generateToken.gerar((Usuario) auth.getPrincipal());
    }
}
