package br.com.diego.pscicologia.web.rest.paciente;

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

    private final AdicionaPaciente adicionaPaciente;
    private final ConsultaPaciente consultaPaciente;

    @Autowired
    public PacienteRest(AdicionaPaciente adicionaPaciente, ConsultaPaciente consultaPaciente) {
        this.adicionaPaciente = adicionaPaciente;
        this.consultaPaciente = consultaPaciente;
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> buscar(@PathVariable String id) throws Exception {
        PacienteDTO dto = consultaPaciente.buscar(id);
        String json = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .writeValueAsString(dto);

        return ResponseEntity.ok(json);
    }

    @PostMapping()
    public ResponseEntity<String> adicionar(@RequestBody AdicionaPacienteHttpDTO httpDTO) {
        AdicionarPaciente comando = criarComandoParaDicionar(httpDTO);
        String retorno = adicionaPaciente.adicionar(comando);

        return ResponseEntity.ok(retorno);
    }

    private static AdicionarPaciente criarComandoParaDicionar(AdicionaPacienteHttpDTO httpDTO) {
        return new AdicionarPaciente(httpDTO.nome, httpDTO.endereco, httpDTO.quantidaDeDiasNoMes, httpDTO.valorPorSessao);
    }
}
