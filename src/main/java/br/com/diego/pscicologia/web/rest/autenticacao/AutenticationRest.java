package br.com.diego.pscicologia.web.rest.autenticacao;

import br.com.diego.pscicologia.servico.autenticacaodeusuario.AutenticadorDeUsuario;
import br.com.diego.pscicologia.servico.autenticacaodeusuario.usuario.AdicionaUsuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AutenticationRest {

    private final AutenticadorDeUsuario autenticadorDeUsuario;
    private final AdicionaUsuario adicionaUsuario;

    @Autowired
    public AutenticationRest(AutenticadorDeUsuario autenticadorDeUsuario,
                             AdicionaUsuario adicionaUsuario) {
        this.autenticadorDeUsuario = autenticadorDeUsuario;
        this.adicionaUsuario = adicionaUsuario;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationHttpDTO httpDTO) {
        String tokenAutenticado = autenticadorDeUsuario.autenticar(httpDTO.username(), httpDTO.password());
        LoginResponseHttpDTO tokenParaRetornar = new LoginResponseHttpDTO(tokenAutenticado);

        return ResponseEntity.ok(tokenParaRetornar);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterHttpDTO httpDTO) throws Exception {
        String confirmacao = adicionaUsuario.executar(httpDTO.username(), httpDTO.password(), httpDTO.role());

        return ResponseEntity.ok(confirmacao);
    }
}
