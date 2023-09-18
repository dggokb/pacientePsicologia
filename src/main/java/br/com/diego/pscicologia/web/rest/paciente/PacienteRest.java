package br.com.diego.pscicologia.web.rest.paciente;

import br.com.diego.pscicologia.servico.paciente.adiciona.AdicionaPaciente;
import br.com.diego.pscicologia.servico.paciente.adiciona.AdicionarPaciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paciente")
public class PacienteRest {

    private AdicionaPaciente adicionaPaciente;

    @Autowired
    public PacienteRest(AdicionaPaciente adicionaPaciente) {
        this.adicionaPaciente = adicionaPaciente;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> adicionar(@RequestBody AdicionaPacienteHttpDTO httpDTO) {
        AdicionarPaciente comando = criarComandoParaDicionar(httpDTO);
        String retorno = adicionaPaciente.adicionar(comando);

        return ResponseEntity.ok(retorno);
    }

    private static AdicionarPaciente criarComandoParaDicionar(AdicionaPacienteHttpDTO httpDTO) {
        return new AdicionarPaciente(httpDTO.nome, httpDTO.endereco, httpDTO.quantidaDeDiasNoMes, httpDTO.valorPorSessao);
    }
}
