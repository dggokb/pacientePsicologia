package br.com.diego.pscicologia.dominio.paciente;

public enum Tipo {
    VALOR_FIXO("Valor fixo"),
    VALOR_POR_SESSAO("Valor por sessão");

    private final String descricao;

    Tipo(String descricao) {
        this.descricao = descricao;
    }

    public Boolean ehValorFixo() {
        return this.equals(Tipo.VALOR_FIXO);
    }

    public Boolean ehValorPorSessao() {
        return this.equals(Tipo.VALOR_POR_SESSAO);
    }

    public String getDescricao() {
        return descricao;
    }
}
