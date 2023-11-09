package br.com.diego.psicologia.servico.mes;

import br.com.diego.psicologia.comum.FiltroDeConsulta;
import br.com.diego.psicologia.comum.Mes;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultaMesesConcreto implements ConsultaMeses {

    @Override
    public List<MesDTO> consultar(FiltroDeConsulta filtro) throws Exception {
        return Arrays.stream(Mes.values()).map(mes -> {
            MesDTO dto = new MesDTO();
            dto.name = mes.name();
            dto.descricao = mes.getDescricao();

            return dto;
        }).collect(Collectors.toList());
    }
}
