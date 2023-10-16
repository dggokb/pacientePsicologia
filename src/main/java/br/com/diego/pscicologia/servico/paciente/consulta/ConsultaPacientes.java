package br.com.diego.pscicologia.servico.paciente.consulta;

import br.com.diego.pscicologia.comum.ServicoDeAplicacaoDeConsulta;

import java.util.List;

public interface ConsultaPacientes extends ServicoDeAplicacaoDeConsulta<List<PacienteDTO>, FiltroDeConsultaDePaciente> {
}