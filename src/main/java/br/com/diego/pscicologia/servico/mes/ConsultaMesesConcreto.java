package br.com.diego.pscicologia.servico.mes;

import br.com.diego.pscicologia.comum.Mes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultaMesesConcreto implements ConsultaMeses {
    @Transactional(readOnly = true)
    @Override
    public List<MesDTO> buscarTodos() {
        return Arrays.stream(Mes.values()).map(mes -> {
            MesDTO dto = new MesDTO();
            dto.name = mes.name();
            dto.descricao = mes.getDescricao();

            return dto;
        }).collect(Collectors.toList());
    }
}
