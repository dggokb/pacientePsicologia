package br.com.diego.psicologia.servico.paciente.consulta;

import java.util.ArrayList;
import java.util.List;

public class PacienteDTO {
    public String id;
    public String nome;
    public String endereco;
    public List<ValorDTO> valores = new ArrayList<>();
    public String dataDeInicio;
    public Boolean inativo;
    public String tipo;
}
