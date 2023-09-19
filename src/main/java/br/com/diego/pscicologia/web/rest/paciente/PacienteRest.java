package br.com.diego.pscicologia.web.rest.paciente;

import br.com.diego.pscicologia.comum.SerializadorDeObjetoJson;
import br.com.diego.pscicologia.servico.paciente.adiciona.AdicionaPaciente;
import br.com.diego.pscicologia.servico.paciente.adiciona.AdicionarPaciente;
import br.com.diego.pscicologia.servico.paciente.altera.AlteraPaciente;
import br.com.diego.pscicologia.servico.paciente.altera.AlterarPaciente;
import br.com.diego.pscicologia.servico.paciente.consulta.ConsultaPaciente;
import br.com.diego.pscicologia.servico.paciente.consulta.ConsultaPacientes;
import br.com.diego.pscicologia.servico.paciente.consulta.PacienteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "paciente",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class PacienteRest {

    private final ConsultaPaciente consultaPaciente;
    private final ConsultaPacientes consultaPacientes;
    private final AdicionaPaciente adicionaPaciente;
    private final AlteraPaciente alteraPaciente;

    @Autowired
    public PacienteRest(ConsultaPaciente consultaPaciente,
                        ConsultaPacientes consultaPacientes,
                        AdicionaPaciente adicionaPaciente,
                        AlteraPaciente alteraPaciente) {
        this.consultaPaciente = consultaPaciente;
        this.consultaPacientes = consultaPacientes;
        this.adicionaPaciente = adicionaPaciente;
        this.alteraPaciente = alteraPaciente;
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> buscar(@PathVariable String id) throws Exception {
        PacienteDTO dto = consultaPaciente.buscar(id);
        String json = SerializadorDeObjetoJson.serializar(dto);

        return ResponseEntity.ok(json);
    }

    @GetMapping()
    public ResponseEntity<String> buscarTodos() throws Exception {
        List<PacienteDTO> dtos = consultaPacientes.buscarTodos();
        String json = SerializadorDeObjetoJson.serializar(dtos);

        return ResponseEntity.ok(json);
    }

    @PostMapping()
    public ResponseEntity<String> adicionar(@RequestBody AdicionaPacienteHttpDTO httpDTO) {
        AdicionarPaciente comando = criarComandoParaAdicionar(httpDTO);
        String retorno = adicionaPaciente.adicionar(comando);

        return ResponseEntity.ok(retorno);
    }

    @PutMapping()
    public ResponseEntity alterar(@RequestBody AlteraPacienteHttpDTO httpDTO) throws Exception {
        AlterarPaciente comando = criarComandoParaAlterar(httpDTO);
        alteraPaciente.alterar(comando);

        return ResponseEntity.ok().build();
    }

    private static AdicionarPaciente criarComandoParaAdicionar(AdicionaPacienteHttpDTO httpDTO) {
        return new AdicionarPaciente(httpDTO.nome, httpDTO.endereco, httpDTO.quantidaDeDiasNoMes, httpDTO.valorPorSessao);
    }

    private static AlterarPaciente criarComandoParaAlterar(AlteraPacienteHttpDTO httpDTO) {
        return new AlterarPaciente(httpDTO.id, httpDTO.endereco, httpDTO.valorPorSessao);
    }
}
