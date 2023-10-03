package br.com.diego.pscicologia.web.rest.mes;

import br.com.diego.pscicologia.PscicologiaApplication;
import br.com.diego.pscicologia.comum.SerializadorDeObjetoJson;
import br.com.diego.pscicologia.servico.mes.ConsultaMeses;
import br.com.diego.pscicologia.servico.mes.MesDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MesRest.class)
class MesRestTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ConsultaMeses consultaMeses;

    private static String PATH = "/mes";

    @Test
    void deveSerOPossivelConsultarTodosOsMeses() throws Exception {
        MesDTO primeiroMes = criarMesDTO("JANEIRO", "Janeiro");
        MesDTO segundoMes = criarMesDTO("FEVEREIRO", "Fevereiro");
        List<MesDTO> dtos = Arrays.asList(primeiroMes, segundoMes);
        String retornoEsperadoEmJson = SerializadorDeObjetoJson.serializar(dtos);
        Mockito.when(consultaMeses.consultar(null)).thenReturn(dtos);

        ResultActions retornoEsperado = mvc.perform(MockMvcRequestBuilders
                .get(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        retornoEsperado.andExpect(status().isOk());
        retornoEsperado.andExpect(content().json(retornoEsperadoEmJson));
    }

    private MesDTO criarMesDTO(String name, String descricao) {
        MesDTO dto = new MesDTO();
        dto.name = name;
        dto.descricao = descricao;

        return dto;
    }
}