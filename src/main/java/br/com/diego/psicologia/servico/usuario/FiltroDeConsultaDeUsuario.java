package br.com.diego.psicologia.servico.usuario;

import br.com.diego.psicologia.comum.FiltroDeConsulta;

public class FiltroDeConsultaDeUsuario implements FiltroDeConsulta {

    private String id;

    public FiltroDeConsultaDeUsuario(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
