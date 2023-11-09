package br.com.diego.psicologia.servico.mes;

import br.com.diego.psicologia.comum.FiltroDeConsulta;
import br.com.diego.psicologia.comum.ServicoDeAplicacaoDeConsulta;

import java.util.List;

public interface ConsultaMeses extends ServicoDeAplicacaoDeConsulta<List<MesDTO>, FiltroDeConsulta> {

}
