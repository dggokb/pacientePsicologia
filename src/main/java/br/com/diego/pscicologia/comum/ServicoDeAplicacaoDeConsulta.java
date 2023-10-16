package br.com.diego.pscicologia.comum;

import org.springframework.transaction.annotation.Transactional;

public interface ServicoDeAplicacaoDeConsulta<D, F extends FiltroDeConsulta> {
    @Transactional(readOnly = true)
    D consultar(F filtro) throws Exception;
}
