package br.com.diego.psicologia.comum;

import org.springframework.transaction.annotation.Transactional;

public interface ServicoDeAplicacaoDeComando<C extends Comando> {
    @Transactional(rollbackFor = Exception.class)
    String executar(C comando) throws Exception;
}
