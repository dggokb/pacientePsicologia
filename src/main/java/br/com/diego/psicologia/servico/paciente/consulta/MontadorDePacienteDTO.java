package br.com.diego.psicologia.servico.paciente.consulta;

import br.com.diego.psicologia.comum.DateUtils;
import br.com.diego.psicologia.dominio.paciente.Paciente;
import br.com.diego.psicologia.dominio.paciente.Valor;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MontadorDePacienteDTO {

    public PacienteDTO montar(Paciente paciente) {
        validarPaciente(paciente);

        PacienteDTO dto = new PacienteDTO();
        dto.id = paciente.getId();
        dto.nome = paciente.getNome();
        dto.endereco = paciente.getEndereco();
        dto.valores = montarValoresDTO(paciente, dto);
        dto.dataDeInicio = DateUtils.obterDataFormatada(paciente.getDataDeInicio());
        dto.inativo = paciente.getInativo();
        dto.tipo = paciente.obterDescricaoDoTipo();

        return dto;
    }

    private void validarPaciente(Paciente paciente) {
        if (Objects.isNull(paciente)) {
            throw new RuntimeException("É necessário informar o paciente para montar os dados da consulta.");
        }
    }

    private List<ValorDTO> montarValoresDTO(Paciente paciente, PacienteDTO dto) {
        return dto.valores = paciente.getValores().stream()
                .sorted(Comparator.comparing(Valor::getMes))
                .map(valor -> {
                    ValorDTO valorDTO = new ValorDTO();
                    valorDTO.valorPorSessao = valor.getValorPorSessao().valor();
                    if (Objects.nonNull(valor.getQuantidadeDeDiasNoMes())) {
                        valorDTO.quantidaDeDiasNoMes = valor.getQuantidadeDeDiasNoMes().valor().intValue();
                    }
                    valorDTO.mes = valor.getMes().getDescricao();
                    valorDTO.ano = valor.getAno();
                    valorDTO.datasDasSessoes = valor.getDatasDasSessoes().stream().map(DateUtils::obterDataFormatada).collect(Collectors.toList());

                    return valorDTO;
                }).collect(Collectors.toList());
    }
}
