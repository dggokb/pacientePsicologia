package br.com.diego.pscicologia.servico.paciente.consulta;


import br.com.diego.pscicologia.dominio.paciente.Paciente;
import br.com.diego.pscicologia.dominio.paciente.PacienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class ConsultaPacienteConcreto implements ConsultaPaciente {

    private final PacienteRepositorio pacienteRepositorio;

    @Autowired
    public ConsultaPacienteConcreto(PacienteRepositorio pacienteRepositorio) {
        this.pacienteRepositorio = pacienteRepositorio;
    }

    @Transactional(readOnly = true)
    @Override
    public PacienteDTO buscar(String id) throws Exception {
        validarSePossuiIdentificador(id);
        Optional<Paciente> pacienteObtido = pacienteRepositorio.findById(id);
        validarSePacienteFoiEncontrado(pacienteObtido);

        return criarDTO(pacienteObtido.get());
    }

    private void validarSePossuiIdentificador(String id) throws Exception {
        if (Objects.isNull(id)){
            throw  new Exception("É necessário informar o paciente para consulta.");
        }
    }

    private static void validarSePacienteFoiEncontrado(Optional<Paciente> pacienteObtido) throws Exception {
        if (pacienteObtido.isEmpty()) {
            throw new Exception("Não foi possível contrar o paciente.");
        }
    }

    private PacienteDTO criarDTO(Paciente pacienteObtido) {
        PacienteDTO dto = new PacienteDTO();
        dto.id = pacienteObtido.getId();
        dto.nome = pacienteObtido.getNome();
        dto.endereco = pacienteObtido.getEndereco();
        dto.quantidaDeDiasNoMes = pacienteObtido.getQuantidaDeDiasNoMes().valor().intValue();
        dto.valorPorSessao = pacienteObtido.getValorPorSessao().valor();
        dto.dataDeInicio = pacienteObtido.getDataDeInicio();
        dto.inativo = pacienteObtido.getInativo();
        dto.tipo = pacienteObtido.obterDescricaoDoTipo();

        return dto;
    }
}
