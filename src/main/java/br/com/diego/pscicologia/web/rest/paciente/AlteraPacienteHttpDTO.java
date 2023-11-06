package br.com.diego.pscicologia.web.rest.paciente;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class AlteraPacienteHttpDTO {
    public String id;
    public String nome;
    public String endereco;
    public BigDecimal valorPorSessao;
    public String mes;
    public Integer ano;
    public String tipo;
    public List<Date> datasDasSessoes;
}
