package br.com.diego.psicologia.dominio.usuario;

import br.com.diego.psicologia.comum.Entidade;
import br.com.diego.psicologia.comum.ExcecaoDeCampoObrigatorio;
import com.mongodb.lang.NonNull;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class Usuario extends Entidade implements UserDetails {

    @Indexed(unique = true)
    @NonNull
    private String username;
    @NonNull
    @Indexed(unique = true)
    private String password;
    private TipoDeUsuario role;

    public Usuario(String username, String password, TipoDeUsuario role) {
        validarCamposObrigatorios(username, password, role);
        this.username = username;
        this.password = password;
        this.role = role;
    }

    private void validarCamposObrigatorios(String username, String password, TipoDeUsuario role) {
        new ExcecaoDeCampoObrigatorio()
                .quandoNulo(username, "Não é possível criar um usuário sem informar o username.")
                .quandoNulo(password, "Não é possível criar um usuário sem informar o password.")
                .quandoNulo(role, "Não é possível criar um usuário sem informar o tipo de usuário.")
                .entaoDispara();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role.ehAdmin()) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public TipoDeUsuario getRole() {
        return role;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
