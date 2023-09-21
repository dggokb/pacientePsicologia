package br.com.diego.pscicologia.web.rest.paciente;

import java.math.BigDecimal;

public class AdicionaPacienteHttpDTO {
    public String nome;
    public String endereco;
    public Integer quantidaDeDiasNoMes;
    public BigDecimal valorPorSessao;
    public String mes;
    public Integer ano;
    public String tipo;
}
