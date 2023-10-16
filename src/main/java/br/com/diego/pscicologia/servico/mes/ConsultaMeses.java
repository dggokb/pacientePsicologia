package br.com.diego.pscicologia.servico.mes;

import br.com.diego.pscicologia.comum.FiltroDeConsulta;
import br.com.diego.pscicologia.comum.ServicoDeAplicacaoDeConsulta;

import java.util.List;

public interface ConsultaMeses extends ServicoDeAplicacaoDeConsulta<List<MesDTO>, FiltroDeConsulta> {

}
