package br.com.diego.pscicologia.servico.paciente.consulta;

import br.com.diego.pscicologia.dominio.paciente.Paciente;

import java.util.Objects;

public class MontadorDePacienteDTO {

    public PacienteDTO montar(Paciente paciente) throws Exception {
        validarPaciente(paciente);

        PacienteDTO dto = new PacienteDTO();
        dto.id = paciente.getId();
        dto.nome = paciente.getNome();
        dto.endereco = paciente.getEndereco();
        dto.valorPorSessao = paciente.getValorPorSessao().valor();
        if (Objects.nonNull(paciente.getQuantidadeDeDiasNoMes())) {
            dto.quantidaDeDiasNoMes = paciente.getQuantidadeDeDiasNoMes().valor().intValue();
        }
        dto.dataDeInicio = paciente.getDataDeInicio();
        dto.inativo = paciente.getInativo();
        dto.tipo = paciente.obterDescricaoDoTipo();

        return dto;
    }

    private static void validarPaciente(Paciente paciente) throws Exception {
        if(Objects.isNull(paciente)){
            throw new Exception("É necessário informar o paciente para montar os dados da consulta.");
        }
    }
}
