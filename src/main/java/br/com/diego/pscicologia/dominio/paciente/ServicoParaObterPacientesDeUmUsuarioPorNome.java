package br.com.diego.pscicologia.dominio.paciente;

import br.com.diego.pscicologia.comum.ExcecaoDeCampoObrigatorio;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

public class ServicoParaObterPacientesDeUmUsuarioPorNome {

    private final PacienteRepositorio pacienteRepositorio;

    @Autowired
    public ServicoParaObterPacientesDeUmUsuarioPorNome(PacienteRepositorio pacienteRepositorio) {
        this.pacienteRepositorio = pacienteRepositorio;
    }

    public List<Paciente> obter(String nome, String usuarioId) {
        valiadrCampoObrigatorio(usuarioId);
        if (Objects.isNull(nome) || nome.isEmpty()) {
            return pacienteRepositorio.buscarDoUsuario(usuarioId);
        }
        return pacienteRepositorio.buscarTodos(nome, usuarioId);
    }

    private void valiadrCampoObrigatorio(String usuarioId) {
        new ExcecaoDeCampoObrigatorio()
                .quandoNulo(usuarioId, "Não foi possível buscar os pacientes, pois não foi informado o usuário.")
                .entaoDispara();
    }
}
