package br.com.diego.psicologia.servico.paciente.consulta;

import br.com.diego.psicologia.comum.ServicoDeAplicacaoDeConsulta;

import java.util.List;

public interface ConsultaPaciente extends ServicoDeAplicacaoDeConsulta<List<PacienteDTO>, FiltroDeConsultaDePaciente> {
}
