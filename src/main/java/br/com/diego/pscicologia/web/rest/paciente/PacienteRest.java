package br.com.diego.pscicologia.web.rest.paciente;

import br.com.diego.pscicologia.comum.SerializadorDeObjetoJson;
import br.com.diego.pscicologia.servico.paciente.adiciona.AdicionaPaciente;
import br.com.diego.pscicologia.servico.paciente.adiciona.AdicionarPaciente;
import br.com.diego.pscicologia.servico.paciente.consulta.ConsultaPaciente;
import br.com.diego.pscicologia.servico.paciente.consulta.PacienteDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/paciente",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class PacienteRest {

    private final ConsultaPaciente consultaPaciente;
    private final AdicionaPaciente adicionaPaciente;

    @Autowired
    public PacienteRest(ConsultaPaciente consultaPaciente, AdicionaPaciente adicionaPaciente) {
        this.consultaPaciente = consultaPaciente;
        this.adicionaPaciente = adicionaPaciente;
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> buscar(@PathVariable String id) throws Exception {
        PacienteDTO dto = consultaPaciente.buscar(id);
        String json = SerializadorDeObjetoJson.serializar(dto);

        return ResponseEntity.ok(json);
    }

    @PostMapping()
    public ResponseEntity<String> adicionar(@RequestBody AdicionaPacienteHttpDTO httpDTO) {
        AdicionarPaciente comando = criarComandoParaAdicionar(httpDTO);
        String retorno = adicionaPaciente.adicionar(comando);

        return ResponseEntity.ok(retorno);
    }

    private static AdicionarPaciente criarComandoParaAdicionar(AdicionaPacienteHttpDTO httpDTO) {
        return new AdicionarPaciente(httpDTO.nome, httpDTO.endereco, httpDTO.quantidaDeDiasNoMes, httpDTO.valorPorSessao);
    }
}
