package br.com.diego.pscicologia.web.rest.paciente;

import br.com.diego.pscicologia.servico.paciente.adiciona.AdicionaPaciente;
import br.com.diego.pscicologia.servico.paciente.adiciona.AdicionarPaciente;
import br.com.diego.pscicologia.servico.paciente.consulta.ConsultaPaciente;
import br.com.diego.pscicologia.servico.paciente.consulta.PacienteDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PacienteRest.class)
class PacienteRestTest {

    private static String PATH = "/paciente";

    @Autowired
    private PacienteRest pacienteRest;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AdicionaPaciente adicionaPaciente;

    @MockBean
    private ConsultaPaciente consultaPaciente;

    @Test
    void deveSerPossivelAdicionarUmPaciente() throws Exception {
        AdicionaPacienteHttpDTO httpDTO = new AdicionaPacienteHttpDTO();
        httpDTO.nome = "Teste";
        httpDTO.endereco = "Teste";
        httpDTO.quantidaDeDiasNoMes = 10;
        httpDTO.valorPorSessao = BigDecimal.TEN;
        ArgumentCaptor<AdicionarPaciente> captor = ArgumentCaptor.forClass(AdicionarPaciente.class);
        Mockito.when(adicionaPaciente.adicionar(captor.capture())).thenReturn(UUID.randomUUID().toString());

        ResultActions retornoEsperado = mvc.perform(MockMvcRequestBuilders
                .post(PATH)
                .content(asJsonString(httpDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        retornoEsperado.andExpect(status().isOk());
    }

    @Test
    void deveSerOPossivelConsultarUmPaciente() throws Exception {
        PacienteDTO dto = new PacienteDTO();
        dto.id = "1";
        String retornoEsperadoEmJson = asJsonString(dto);
        Mockito.when(consultaPaciente.buscar(dto.id)).thenReturn(dto);

        ResultActions retornoEsperado = mvc.perform(MockMvcRequestBuilders
                .get(PATH + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        retornoEsperado.andExpect(status().isOk());
        retornoEsperado.andExpect(content().json(retornoEsperadoEmJson));
    }

    @Test
    void deveSerPossivelAdicionarUmPacienteTestandoDeOutraManeira() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ArgumentCaptor<AdicionarPaciente> captor = ArgumentCaptor.forClass(AdicionarPaciente.class);
        String retornoEsperado = UUID.randomUUID().toString();
        Mockito.when(adicionaPaciente.adicionar(captor.capture())).thenReturn(retornoEsperado);
        AdicionaPacienteHttpDTO httpDTO = new AdicionaPacienteHttpDTO();
        httpDTO.nome = "Teste";
        httpDTO.endereco = "Teste";
        httpDTO.quantidaDeDiasNoMes = 10;
        httpDTO.valorPorSessao = BigDecimal.TEN;
        ResponseEntity<String> response = pacienteRest.adicionar(httpDTO);

        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
        Assertions.assertThat(response.getBody()).isEqualTo(retornoEsperado);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}