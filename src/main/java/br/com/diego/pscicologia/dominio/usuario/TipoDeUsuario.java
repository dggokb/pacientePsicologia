package br.com.diego.pscicologia.dominio.usuario;

public enum TipoDeUsuario {
    ADMIN("Admin"),
    USER("User");

    private String role;

    TipoDeUsuario(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public boolean ehAdmin() {
        return this.equals(TipoDeUsuario.ADMIN);
    }
}
