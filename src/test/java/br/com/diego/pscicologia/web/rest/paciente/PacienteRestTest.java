package br.com.diego.pscicologia.web.rest.paciente;

import br.com.diego.pscicologia.comum.SerializadorDeObjetoJson;
import br.com.diego.pscicologia.servico.paciente.adiciona.AdicionaPaciente;
import br.com.diego.pscicologia.servico.paciente.adiciona.AdicionarPaciente;
import br.com.diego.pscicologia.servico.paciente.altera.AlteraPaciente;
import br.com.diego.pscicologia.servico.paciente.altera.AlterarPaciente;
import br.com.diego.pscicologia.servico.paciente.ativa.AtivaPaciente;
import br.com.diego.pscicologia.servico.paciente.consulta.*;
import br.com.diego.pscicologia.servico.paciente.inativa.InativaPaciente;
import br.com.diego.pscicologia.web.rest.base.TestBaseApi;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PacienteRest.class)
@AutoConfigureMockMvc(addFilters = false)
class PacienteRestTest extends TestBaseApi {

    private static String PATH = "/paciente";

    @Autowired
    private PacienteRest pacienteRest;

    @MockBean
    private ConsultaPacientePorId consultaPacientePorId;

    @MockBean
    private ConsultaPacientes consultaPacientes;

    @MockBean
    private AdicionaPaciente adicionaPaciente;

    @MockBean
    private AlteraPaciente alteraPaciente;

    @MockBean
    private AtivaPaciente ativaPaciente;

    @MockBean
    private InativaPaciente inativaPaciente;

    @MockBean
    private ConsultaTipoDePaciente consultaTipoDePaciente;

    @MockBean
    private ConsultaValorTotalDeFechamentoDoPaciente consultaValorTotalDeFechamentoDoPaciente;

    @MockBean
    private ConsultaPaciente consultaPaciente;


    @Test
    void deveSerPossivelAdicionarUmPaciente() throws Exception {
        AdicionaPacienteHttpDTO httpDTO = criarAdicionaPacienteHttpDTO();
        ArgumentCaptor<AdicionarPaciente> captor = ArgumentCaptor.forClass(AdicionarPaciente.class);
        Mockito.when(adicionaPaciente.executar(captor.capture())).thenReturn(UUID.randomUUID().toString());

        ResultActions retornoEsperado = mvc.perform(MockMvcRequestBuilders
                .post(PATH)
                .content(SerializadorDeObjetoJson.serializar(httpDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        retornoEsperado.andExpect(status().isOk());
        AdicionarPaciente comandoCapturado = captor.getValue();
        Assertions.assertThat(comandoCapturado.getNome()).isEqualTo(httpDTO.nome);
        Assertions.assertThat(comandoCapturado.getEndereco()).isEqualTo(httpDTO.endereco);
        Assertions.assertThat(comandoCapturado.getValorPorSessao().valor()).isEqualTo(httpDTO.valorPorSessao);
        Assertions.assertThat(comandoCapturado.getDatasDasSessoes()).containsOnly(LocalDate.now());
    }

    @Test
    void deveSerOPossivelConsultarUmPaciente() throws Exception {
        PacienteDTO dto = criarPacienteDTO("1");
        ArgumentCaptor<FiltroDeConsultaDePaciente> filtroCapturado = ArgumentCaptor.forClass(FiltroDeConsultaDePaciente.class);
        String retornoEsperadoEmJson = SerializadorDeObjetoJson.serializar(dto);
        Mockito.when(consultaPacientePorId.consultar(filtroCapturado.capture())).thenReturn(dto);

        ResultActions retornoEsperado = mvc.perform(MockMvcRequestBuilders
                .get(PATH + "/" + dto.id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        retornoEsperado.andExpect(status().isOk());
        retornoEsperado.andExpect(content().json(retornoEsperadoEmJson));
    }

    @Test
    void deveSerOPossivelConsultarOValorTotalDoFechamentoDeUmPaciente() throws Exception {
        ValorTotalDeFechamentoDoPacienteDTO dto = new ValorTotalDeFechamentoDoPacienteDTO();
        dto.valorTotal = BigDecimal.TEN;
        ArgumentCaptor<FiltroDeConsultaDeValorTotalDeFechamentoDoPaciente> filtroCapturado = ArgumentCaptor.forClass(FiltroDeConsultaDeValorTotalDeFechamentoDoPaciente.class);
        String retornoEsperadoEmJson = SerializadorDeObjetoJson.serializar(dto);
        Mockito.when(consultaValorTotalDeFechamentoDoPaciente.consultar(filtroCapturado.capture())).thenReturn(dto);
        String mes = "JANEIRO";
        String ano = "2023";

        ResultActions retornoEsperado = mvc.perform(MockMvcRequestBuilders
                .get(PATH + "/fechamento/" + UUID.randomUUID().toString())
                .param("mes", mes)
                .param("ano", ano)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        retornoEsperado.andExpect(status().isOk());
        retornoEsperado.andExpect(content().json(retornoEsperadoEmJson));
    }

    @Test
    void deveSerOPossivelConsultarTodosPacientes() throws Exception {
        PacienteDTO primeiroDTO = criarPacienteDTO("1");
        PacienteDTO segundoDTO = criarPacienteDTO("2");
        List<PacienteDTO> dtos = Arrays.asList(primeiroDTO, segundoDTO);
        String retornoEsperadoEmJson = SerializadorDeObjetoJson.serializar(dtos);
        ArgumentCaptor<FiltroDeConsultaDePaciente> filtroCapturado = ArgumentCaptor.forClass(FiltroDeConsultaDePaciente.class);
        Mockito.when(consultaPacientes.consultar(filtroCapturado.capture())).thenReturn(dtos);

        ResultActions retornoEsperado = mvc.perform(MockMvcRequestBuilders
                .get(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        retornoEsperado.andExpect(status().isOk());
        retornoEsperado.andExpect(content().json(retornoEsperadoEmJson));
    }

    @Test
    void deveSerOPossivelConsultarTiposDePaciente() throws Exception {
        TipoDePacienteDTO dto = new TipoDePacienteDTO();
        dto.name = "VALOR_FIXO";
        dto.descricao = "Valor fixo";
        List<TipoDePacienteDTO> dtos = Collections.singletonList(dto);
        String retornoEsperadoEmJson = SerializadorDeObjetoJson.serializar(dtos);
        Mockito.when(consultaTipoDePaciente.buscar()).thenReturn(dtos);

        ResultActions retornoEsperado = mvc.perform(MockMvcRequestBuilders
                .get(PATH + "/tipo")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        retornoEsperado.andExpect(status().isOk());
        retornoEsperado.andExpect(content().json(retornoEsperadoEmJson));
    }

    @Test
    void deveSerPossivelAlterarUmPaciente() throws Exception {
        AlteraPacienteHttpDTO httpDTO = criarAlteraPacienteHttpDTO();
        ArgumentCaptor<AlterarPaciente> captor = ArgumentCaptor.forClass(AlterarPaciente.class);
        Mockito.when(alteraPaciente.executar(captor.capture())).thenReturn(UUID.randomUUID().toString());

        ResultActions retornoEsperado = mvc.perform(MockMvcRequestBuilders
                .put(PATH)
                .content(SerializadorDeObjetoJson.serializar(httpDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        retornoEsperado.andExpect(status().isOk());
        AlterarPaciente comandoCapturado = captor.getValue();
        Assertions.assertThat(comandoCapturado.getId()).isEqualTo(httpDTO.id);
        Assertions.assertThat(comandoCapturado.getEndereco()).isEqualTo(httpDTO.endereco);
    }

    @Test
    void deveSerPossivelAtivarUmPaciente() throws Exception {
        String id = UUID.randomUUID().toString();
        Mockito.doNothing().when(ativaPaciente).ativar(id);

        ResultActions retornoEsperado = mvc.perform(MockMvcRequestBuilders
                .put(PATH + "/ativar/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        retornoEsperado.andExpect(status().isOk());
    }

    @Test
    void deveSerPossivelInativarUmPaciente() throws Exception {
        String id = UUID.randomUUID().toString();
        Mockito.doNothing().when(inativaPaciente).inativar(id);

        ResultActions retornoEsperado = mvc.perform(MockMvcRequestBuilders
                .delete(PATH + "/inativar/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        retornoEsperado.andExpect(status().isOk());
    }

    @Test
    void deveSerPossivelAdicionarUmPacienteTestandoDeOutraManeira() throws Exception {
        int codigoDeRetornoEsperado = 200;
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ArgumentCaptor<AdicionarPaciente> captor = ArgumentCaptor.forClass(AdicionarPaciente.class);
        String retornoEsperado = UUID.randomUUID().toString();
        Mockito.when(adicionaPaciente.executar(captor.capture())).thenReturn(retornoEsperado);
        AdicionaPacienteHttpDTO httpDTO = criarAdicionaPacienteHttpDTO();
        ResponseEntity<String> response = pacienteRest.adicionar(httpDTO);

        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(codigoDeRetornoEsperado);
        Assertions.assertThat(response.getBody()).isEqualTo(retornoEsperado);
    }

    @Test
    void deveSerOPossivelConsultarPacientePorNome() throws Exception {
        PacienteDTO primeiroDTO = criarPacienteDTO("1");
        PacienteDTO segundoDTO = criarPacienteDTO("2");
        List<PacienteDTO> dtos = Arrays.asList(primeiroDTO, segundoDTO);
        ArgumentCaptor<FiltroDeConsultaDePaciente> filtroCapturado = ArgumentCaptor.forClass(FiltroDeConsultaDePaciente.class);
        String retornoEsperadoEmJson = SerializadorDeObjetoJson.serializar(dtos);
        Mockito.when(consultaPaciente.consultar(filtroCapturado.capture())).thenReturn(dtos);

        ResultActions retornoEsperado = mvc.perform(MockMvcRequestBuilders
                .get(PATH + "/consultar")
                .param("nome", "Diego")
                .param("usuarioId", UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        retornoEsperado.andExpect(status().isOk());
        retornoEsperado.andExpect(content().json(retornoEsperadoEmJson));
    }

    private AdicionaPacienteHttpDTO criarAdicionaPacienteHttpDTO() {
        AdicionaPacienteHttpDTO httpDTO = new AdicionaPacienteHttpDTO();
        httpDTO.usuarioId = UUID.randomUUID().toString();
        httpDTO.nome = "Teste";
        httpDTO.endereco = "Teste";
        httpDTO.valorPorSessao = BigDecimal.TEN;
        httpDTO.mes = "JANEIRO";
        httpDTO.ano = 2023;
        httpDTO.tipo = "VALOR_POR_SESSAO";
        httpDTO.datasDasSessoes = Collections.singletonList(new Date());

        return httpDTO;
    }

    private PacienteDTO criarPacienteDTO(String id) {
        PacienteDTO dto = new PacienteDTO();
        dto.id = id;

        return dto;
    }

    private AlteraPacienteHttpDTO criarAlteraPacienteHttpDTO() {
        AlteraPacienteHttpDTO httpDTO = new AlteraPacienteHttpDTO();
        httpDTO.id = UUID.randomUUID().toString();
        httpDTO.nome = "Teste";
        httpDTO.endereco = "Teste";
        httpDTO.valorPorSessao = BigDecimal.TEN;
        httpDTO.mes = "JANEIRO";
        httpDTO.ano = 2023;
        httpDTO.tipo = "VALOR_FIXO";
        httpDTO.datasDasSessoes = Collections.singletonList(new Date());;

        return httpDTO;
    }
}