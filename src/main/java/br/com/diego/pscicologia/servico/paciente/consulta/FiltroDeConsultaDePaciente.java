package br.com.diego.pscicologia.servico.paciente.consulta;

import br.com.diego.pscicologia.comum.FiltroDeConsulta;

public class FiltroDeConsultaDePaciente implements FiltroDeConsulta {

    private String id;

    public FiltroDeConsultaDePaciente comId(String id){
        this.id = id;
        return this;
    }

    public String getId() {
        return id;
    }
}
