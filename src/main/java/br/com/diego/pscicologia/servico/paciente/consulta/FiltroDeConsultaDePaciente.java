package br.com.diego.pscicologia.servico.paciente.consulta;

import br.com.diego.pscicologia.comum.FiltroDeConsulta;

public class FiltroDeConsultaDePaciente implements FiltroDeConsulta {

    private String id;
    private String usuarioId;
    private String nome;

    public FiltroDeConsultaDePaciente comId(String id) {
        this.id = id;
        return this;
    }

    public FiltroDeConsultaDePaciente comUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
        return this;
    }

    public FiltroDeConsultaDePaciente comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public String getId() {
        return id;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public String getNome() {
        return nome;
    }
}
