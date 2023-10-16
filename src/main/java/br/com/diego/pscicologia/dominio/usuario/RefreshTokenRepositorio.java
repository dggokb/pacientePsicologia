package br.com.diego.pscicologia.dominio.usuario;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RefreshTokenRepositorio extends MongoRepository<RefreshToken, String> {
}
