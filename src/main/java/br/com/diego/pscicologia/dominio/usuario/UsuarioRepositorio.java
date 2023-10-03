package br.com.diego.pscicologia.dominio.usuario;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UsuarioRepositorio extends MongoRepository<Usuario, String> {
    Optional<Usuario> finByUsarname(String usarname);
}
