package br.com.diego.pscicologia.web.rest.paciente;

import br.com.diego.pscicologia.servico.paciente.adiciona.AdicionaPaciente;
import br.com.diego.pscicologia.servico.paciente.adiciona.AdicionarPaciente;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PacienteRest.class)
class PacienteRestTest {

    @Autowired
    private PacienteRest pacienteRest;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AdicionaPaciente adicionaPaciente;

    @Test
    void deveSerPossivelAdicionarUmPaciente() throws Exception {
        AdicionaPacienteHttpDTO httpDTO = new AdicionaPacienteHttpDTO();
        httpDTO.nome = "Teste";
        httpDTO.endereco = "Teste";
        httpDTO.quantidaDeDiasNoMes = 10;
        httpDTO.valorPorSessao = BigDecimal.TEN;
        ArgumentCaptor<AdicionarPaciente> captor = ArgumentCaptor.forClass(AdicionarPaciente.class);
        Mockito.when(adicionaPaciente.adicionar(captor.capture())).thenReturn(UUID.randomUUID().toString());

        mvc.perform(MockMvcRequestBuilders
                        .post("/paciente")
                        .content(asJsonString(httpDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deveSerPossivelAdicionarUmPaciente2() throws Exception {
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