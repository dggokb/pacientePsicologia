package br.com.diego.pscicologia.servico.paciente.consulta;

import br.com.diego.pscicologia.dominio.paciente.Paciente;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MontadorDePacienteDTO {

    public PacienteDTO montar(Paciente paciente) throws Exception {
        validarPaciente(paciente);

        PacienteDTO dto = new PacienteDTO();
        dto.id = paciente.getId();
        dto.nome = paciente.getNome();
        dto.endereco = paciente.getEndereco();
        dto.valores = montarValoresDTO(paciente, dto);
        dto.dataDeInicio = paciente.getDataDeInicio();
        dto.inativo = paciente.getInativo();
        dto.tipo = paciente.obterDescricaoDoTipo();

        return dto;
    }

    private static void validarPaciente(Paciente paciente) throws Exception {
        if (Objects.isNull(paciente)) {
            throw new Exception("É necessário informar o paciente para montar os dados da consulta.");
        }
    }

    private List<ValorDTO> montarValoresDTO(Paciente paciente, PacienteDTO dto) {
        return dto.valores = paciente.getValores().stream().map(valor -> {
            ValorDTO valorDTO = new ValorDTO();
            valorDTO.valorPorSessao = valor.getValorPorSessao().valor();
            if (Objects.nonNull(valor.getQuantidadeDeDiasNoMes())) {
                valorDTO.quantidaDeDiasNoMes = valor.getQuantidadeDeDiasNoMes().valor().intValue();
            }
            valorDTO.mes = valor.getMes().getDescricao();
            valorDTO.ano = valor.getAno();

            return valorDTO;
        }).collect(Collectors.toList());
    }
}
