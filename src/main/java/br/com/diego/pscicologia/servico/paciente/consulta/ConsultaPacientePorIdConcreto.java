package br.com.diego.pscicologia.servico.paciente.consulta;


import br.com.diego.pscicologia.dominio.paciente.Paciente;
import br.com.diego.pscicologia.dominio.paciente.PacienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ConsultaPacientePorIdConcreto implements ConsultaPacientePorId {

    private final PacienteRepositorio pacienteRepositorio;

    @Autowired
    public ConsultaPacientePorIdConcreto(PacienteRepositorio pacienteRepositorio) {
        this.pacienteRepositorio = pacienteRepositorio;
    }

    @Override
    public PacienteDTO consultar(FiltroDeConsultaDePaciente filtro) throws Exception {
        validarSePossuiIdentificador(filtro.getId());
        Paciente pacienteObtido = pacienteRepositorio.findById(filtro.getId())
                .orElseThrow(()-> new Exception("Não foi possível contrar o paciente."));

        return new MontadorDePacienteDTO().montar(pacienteObtido);
    }

    private void validarSePossuiIdentificador(String id) throws Exception {
        if (Objects.isNull(id)) {
            throw new Exception("É necessário informar o paciente para consulta.");
        }
    }
}
