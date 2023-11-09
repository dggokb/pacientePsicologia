package br.com.diego.psicologia.web.rest.base;

import br.com.diego.psicologia.porta.adaptador.autenticacao.FiltroDeSeguranca;
import br.com.diego.psicologia.servico.autenticacao.AutenticadorDeUsuario;
import br.com.diego.psicologia.servico.autenticacao.ValidadorDeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
public abstract class TestBaseApi {
    @Autowired
    public MockMvc mvc;

    @MockBean
    private ValidadorDeToken validadorDeToken;

    @MockBean
    private FiltroDeSeguranca filtroDeSeguranca;

    @MockBean
    private AutenticadorDeUsuario autenticadorDeUsuario;
}
