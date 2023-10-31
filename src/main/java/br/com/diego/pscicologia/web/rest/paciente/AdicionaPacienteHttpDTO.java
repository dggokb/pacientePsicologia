package br.com.diego.pscicologia.web.rest.paciente;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class AdicionaPacienteHttpDTO {
    public String usuarioId;
    public String nome;
    public String endereco;
    public BigDecimal valorPorSessao;
    public String mes;
    public Integer ano;
    public String tipo;
    public List<Date> datasDasSessoes;
}
