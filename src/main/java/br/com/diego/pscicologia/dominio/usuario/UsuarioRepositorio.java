package br.com.diego.pscicologia.dominio.usuario;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuarioRepositorio extends MongoRepository<Usuario, String> {

    @Query("{username :?0}")
    Optional<UserDetails> finByUsername(String username);
}
