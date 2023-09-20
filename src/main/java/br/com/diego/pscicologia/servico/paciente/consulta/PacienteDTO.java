package br.com.diego.pscicologia.servico.paciente.consulta;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PacienteDTO {
    public String id;
    public String nome;
    public String endereco;
    public Integer quantidaDeDiasNoMes;
    public BigDecimal valorPorSessao;
    public LocalDate dataDeInicio;
    public Boolean inativo;
    public String tipo;
}
