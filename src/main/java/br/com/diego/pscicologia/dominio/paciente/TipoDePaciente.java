package br.com.diego.pscicologia.dominio.paciente;

public enum TipoDePaciente {
    VALOR_FIXO("Valor fixo"),
    VALOR_POR_SESSAO("Valor por sessão");

    private final String descricao;

    TipoDePaciente(String descricao) {
        this.descricao = descricao;
    }

    public Boolean ehValorFixo() {
        return this.equals(TipoDePaciente.VALOR_FIXO);
    }

    public Boolean ehValorPorSessao() {
        return this.equals(TipoDePaciente.VALOR_POR_SESSAO);
    }

    public String getDescricao() {
        return descricao;
    }
}
