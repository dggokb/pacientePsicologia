package br.com.diego.pscicologia.dominio.paciente;

public enum TipoDoPaciente {
    VALOR_FIXO("Valor fixo"),
    VALOR_MENSAL("Valor mensal");

    private final String descricao;

    TipoDoPaciente(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
