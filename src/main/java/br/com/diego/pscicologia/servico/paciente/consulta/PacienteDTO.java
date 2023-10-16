package br.com.diego.pscicologia.servico.paciente.consulta;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PacienteDTO {
    public String id;
    public String nome;
    public String endereco;
    public List<ValorDTO> valores = new ArrayList<>();
    public LocalDate dataDeInicio;
    public Boolean inativo;
    public String tipo;
}
