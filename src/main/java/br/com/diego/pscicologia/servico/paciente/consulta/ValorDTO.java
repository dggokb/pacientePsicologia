package br.com.diego.pscicologia.servico.paciente.consulta;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ValorDTO {
    public Integer quantidaDeDiasNoMes;
    public BigDecimal valorPorSessao;
    public String mes;
    public Integer ano;
    public List<String> datasDasSessoes;
}
