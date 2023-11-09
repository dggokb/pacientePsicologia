package br.com.diego.psicologia.comum;

import org.springframework.transaction.annotation.Transactional;

public interface ServicoDeAplicacaoDeConsulta<D, F extends FiltroDeConsulta> {
    @Transactional(readOnly = true)
    D consultar(F filtro) throws Exception;
}
