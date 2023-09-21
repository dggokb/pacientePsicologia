package br.com.diego.pscicologia.dominio.paciente;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacienteRepositorio extends MongoRepository<Paciente, String> {
    @Query("{inativo :false}")
    public List<Paciente> buscarAtivos();

    @Query("{inativo :true}")
    public List<Paciente> buscarInativos();

    @Query("{nome :?0}")
    Paciente buscar(String nome);
}
