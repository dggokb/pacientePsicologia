package br.com.diego.pscicologia.comum;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface ServicoDeAplicacaoDeComando<C extends Comando> {
    @Transactional(rollbackFor = Exception.class)
    String executar(C comando) throws Exception;
}
