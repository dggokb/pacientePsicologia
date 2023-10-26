package br.com.diego.pscicologia.porta.adaptador.autenticacao;

import br.com.diego.pscicologia.dominio.usuario.UsuarioRepositorio;
import br.com.diego.pscicologia.servico.autenticacao.ValidadorDeToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
public class FiltroDeSeguranca extends OncePerRequestFilter {

    private final ValidadorDeToken validadorDeToken;
    private final UsuarioRepositorio usuarioRepositorio;

    @Autowired
    public FiltroDeSeguranca(ValidadorDeToken validadorDeToken,
                             UsuarioRepositorio usuarioRepositorio) {
        this.validadorDeToken = validadorDeToken;
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = recuperarToken(request);
        if (Objects.nonNull(token)) {
            String username = validadorDeToken.validar(token);
            UserDetails userDetails = usuarioRepositorio.finByUsername(username).orElseThrow(() -> new RuntimeException("Não econtrou token do usuario"));
            Object credentials = null;
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, credentials, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (Objects.isNull(authHeader)) return null;
        return authHeader.replace("Bearer ", "");
    }
}
