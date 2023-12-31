package br.com.diego.psicologia.web.rest.mes;

import br.com.diego.psicologia.comum.SerializadorDeObjetoJson;
import br.com.diego.psicologia.servico.mes.ConsultaMeses;
import br.com.diego.psicologia.servico.mes.MesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "mes",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE)
public class MesRest {
    private final ConsultaMeses consultaMeses;

    @Autowired
    public MesRest(ConsultaMeses consultaMeses) {
        this.consultaMeses = consultaMeses;
    }

    @GetMapping()
    public ResponseEntity<String> buscarTodos() throws Exception {
        List<MesDTO> dtos = consultaMeses.consultar(null);
        String json = SerializadorDeObjetoJson.serializar(dtos);

        return ResponseEntity.ok(json);
    }
}
