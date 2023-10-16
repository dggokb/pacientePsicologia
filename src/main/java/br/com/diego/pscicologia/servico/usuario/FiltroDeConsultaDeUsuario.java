package br.com.diego.pscicologia.servico.usuario;

import br.com.diego.pscicologia.comum.FiltroDeConsulta;

public class FiltroDeConsultaDeUsuario implements FiltroDeConsulta {

    private String id;

    public FiltroDeConsultaDeUsuario(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
