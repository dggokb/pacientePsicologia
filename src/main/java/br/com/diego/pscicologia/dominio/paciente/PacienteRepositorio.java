package br.com.diego.pscicologia.dominio.paciente;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepositorio extends MongoRepository<Paciente, String> {
}
