package br.com.diego.pscicologia.comum;

public interface ServicoDeAplicacaoDeConsulta <D, F extends FiltroDeConsulta>{
    D consultar (F filtroDeConsulta);
}
