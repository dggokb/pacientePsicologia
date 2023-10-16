package br.com.diego.pscicologia.servico.paciente.consulta;

import br.com.diego.pscicologia.comum.FiltroDeConsulta;
import br.com.diego.pscicologia.comum.Mes;

public class FiltroDeConsultaDeValorTotalDeFechamentoDoPaciente implements FiltroDeConsulta {
    private String id;
    private Mes mes;
    private Integer ano;

    public FiltroDeConsultaDeValorTotalDeFechamentoDoPaciente(String id, String mes, Integer ano) {
        this.id = id;
        this.mes = Mes.valueOf(mes);
        this.ano = ano;
    }

    public String getId() {
        return id;
    }

    public Mes getMes() {
        return mes;
    }

    public Integer getAno() {
        return ano;
    }
}
