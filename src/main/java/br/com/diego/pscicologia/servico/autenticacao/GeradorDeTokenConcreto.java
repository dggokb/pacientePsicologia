package br.com.diego.pscicologia.servico.autenticacao;

import br.com.diego.pscicologia.dominio.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class GeradorDeTokenConcreto implements GeradorDeToken {

    @Value("${api.security.token.secret}")
    private String secret;

    @Override
    public UsuarioAutenticadoDTO gerar(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String token = JWT.create().withIssuer("auth-api")
                    .withSubject(usuario.getUsername())
                    .withExpiresAt(LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")))
                    .sign(algorithm);
            return new UsuarioAutenticadoDTO(usuario.getId(), token);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Não foi possível gerar o token", e);
        }
    }
}
