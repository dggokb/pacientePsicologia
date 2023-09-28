package br.com.diego.pscicologia.servico.paciente.consulta;

import br.com.diego.pscicologia.dominio.paciente.TipoDePaciente;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultaTiposDePacienteConcreto implements ConsultaTipoDePaciente {
    @Transactional(readOnly = true)
    @Override
    public List<TipoDePacienteDTO> buscar() {

        return Arrays.stream(TipoDePaciente.values()).map(tipo -> {
            TipoDePacienteDTO dto = new TipoDePacienteDTO();
            dto.name = tipo.name();
            dto.descricao = tipo.getDescricao();

            return dto;
        }).collect(Collectors.toList());
    }
}
