package br.com.diego.pscicologia.builder;

import br.com.diego.pscicologia.dominio.usuario.TipoDeUsuario;
import br.com.diego.pscicologia.dominio.usuario.Usuario;

import java.util.UUID;

public class UsuarioBuilder {
    private String username;
    private String password;
    private TipoDeUsuario role;

    public UsuarioBuilder() {
        this.username = "Dunha";
        this.password = UUID.randomUUID().toString();
        this.role = TipoDeUsuario.USER;
    }

    public UsuarioBuilder comUsername(String username) {
        this.username = username;
        return this;
    }

    public UsuarioBuilder comPassword(String password) {
        this.password = password;
        return this;
    }

    public UsuarioBuilder comRole(TipoDeUsuario role) {
        this.role = role;
        return this;
    }

    public Usuario criar() {
        return new Usuario(username, password, role);
    }
}
