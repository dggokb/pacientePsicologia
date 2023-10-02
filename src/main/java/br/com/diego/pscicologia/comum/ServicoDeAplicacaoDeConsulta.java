package br.com.diego.pscicologia.comum;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface ServicoDeAplicacaoDeConsulta <D, F extends FiltroDeConsulta>{
    @Transactional(readOnly = true)
    D consultar (F filtro) throws Exception;
}
