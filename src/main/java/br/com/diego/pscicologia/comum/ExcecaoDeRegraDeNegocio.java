package br.com.diego.pscicologia.comum;

public class ExcecaoDeRegraDeNegocio extends IllegalArgumentException {
    public ExcecaoDeRegraDeNegocio(String msg) {
        super(msg);
    }

    public ExcecaoDeRegraDeNegocio(String msg, Exception e) {
        super(msg, e);
    }
}
