package br.com.diego.pscicologia.web.rest.base;

import br.com.diego.pscicologia.porta.adaptador.autenticacao.FiltroDeSeguranca;
import br.com.diego.pscicologia.servico.autenticacaodeusuario.AutenticadorDeUsuario;
import br.com.diego.pscicologia.servico.autenticacaodeusuario.ValidadorDeToken;
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
