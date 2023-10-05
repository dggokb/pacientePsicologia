package br.com.diego.pscicologia.dominio.usuario;

public enum UserRole {
    ADMIN("Admin"),
    USER("User");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
