package br.com.diego.pscicologia.web.rest.autenticacao;

import br.com.diego.pscicologia.servico.autenticacao.AutenticadorDeUsuario;
import br.com.diego.pscicologia.servico.autenticacao.UsuarioAutenticadoDTO;
import br.com.diego.pscicologia.servico.autenticacao.ValidadorDeToken;
import br.com.diego.pscicologia.servico.autenticacao.usuario.AdicionaUsuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AutenticationRest {

    private final AutenticadorDeUsuario autenticaUsuario;
    private final AdicionaUsuario adicionaUsuario;
    private final ValidadorDeToken validadorDeToken;

    @Autowired
    public AutenticationRest(AutenticadorDeUsuario autenticaUsuario,
                             AdicionaUsuario adicionaUsuario,
                             ValidadorDeToken validadorDeToken) {
        this.autenticaUsuario = autenticaUsuario;
        this.adicionaUsuario = adicionaUsuario;
        this.validadorDeToken = validadorDeToken;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationHttpDTO httpDTO) {
        UsuarioAutenticadoDTO usuarioAutenticado = autenticaUsuario.autenticar(httpDTO.username(), httpDTO.password());

        return ResponseEntity.ok(usuarioAutenticado);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterHttpDTO httpDTO) throws Exception {
        String confirmacao = adicionaUsuario.executar(httpDTO.username(), httpDTO.password(), httpDTO.role());

        return ResponseEntity.ok(confirmacao);
    }

    @GetMapping("/validar")
    public ResponseEntity validar(@RequestParam @Valid String token) throws Exception {
        String confirmacao = validadorDeToken.validar(token);

        return ResponseEntity.ok(confirmacao);
    }
}
