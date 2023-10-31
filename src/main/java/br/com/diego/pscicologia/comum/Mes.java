package br.com.diego.pscicologia.comum;

public enum Mes {
    JANEIRO("Janeiro", 1),
    FEVEREIRO("Fevereiro", 2),
    MARÇO("Março", 3),
    ABRIL("Abril", 4),
    MAIO("Maio", 5),
    JUNHO("Junho", 6),
    JULHO("Julho", 7),
    AGOSTO("Agosto", 8),
    SETEMBRO("Setembro", 9),
    OUTUBRO("Outubro", 10),
    NOVEMBRO("Novembro", 11),
    DEZEMBRO("Dezembro", 12);

    private final String descricao;
    private final int numero;

    Mes(String descricao, int numero) {
        this.descricao = descricao;
        this.numero = numero;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getNumero() {
        return numero;
    }
}
