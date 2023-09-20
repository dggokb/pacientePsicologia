package br.com.diego.pscicologia.dominio.paciente;

public enum Tipo {
    VALOR_FIXO("Valor fixo"),
    VALOR_MENSAL("Valor mensal");

    private final String descricao;

    Tipo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
