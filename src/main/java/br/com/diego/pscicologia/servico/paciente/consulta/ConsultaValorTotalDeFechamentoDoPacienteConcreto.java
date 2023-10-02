package br.com.diego.pscicologia.servico.paciente.consulta;

import br.com.diego.pscicologia.comum.Moeda;
import br.com.diego.pscicologia.dominio.paciente.ServicoParaCalcularFechamentoDoPaciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ConsultaValorTotalDeFechamentoDoPacienteConcreto implements ConsultaValorTotalDeFechamentoDoPaciente {

    private final ServicoParaCalcularFechamentoDoPaciente servicoParaCalcularFechamentoDoPaciente;

    @Autowired
    public ConsultaValorTotalDeFechamentoDoPacienteConcreto(ServicoParaCalcularFechamentoDoPaciente ServicoParaCalcularFechamentoDoPaciente) {
        this.servicoParaCalcularFechamentoDoPaciente = ServicoParaCalcularFechamentoDoPaciente;
    }

    @Override
    public ValorTotalDeFechamentoDoPacienteDTO consultar(FiltroDeConsultaDeValorTotalDeFechamentoDoPaciente filtro) throws Exception {
        Moeda valorCalculado = servicoParaCalcularFechamentoDoPaciente.calcular(filtro.getId(), filtro.getMes(), filtro.getAno());
        validarValorCalculado(valorCalculado);
        ValorTotalDeFechamentoDoPacienteDTO dto = new ValorTotalDeFechamentoDoPacienteDTO();
        dto.valorTotal = valorCalculado.valor();

        return dto;
    }

    private void validarValorCalculado(Moeda valorObtido) throws Exception {
        if(Objects.isNull(valorObtido)){
            throw new Exception("Não foi possível calcular o fechamendo do paciente.");
        }
    }
}
