package br.com.diego.pscicologia.dominio.paciente;

import br.com.diego.pscicologia.comum.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class PacienteFabrica {

    private final PacienteRepositorio pacienteRepositorio;

    public PacienteFabrica(PacienteRepositorio pacienteRepositorio) {
        this.pacienteRepositorio = pacienteRepositorio;
    }

    public Paciente fabricar(String usuarioId,
                             String nome,
                             String endereco,
                             Moeda valorPorSessao,
                             Mes mes,
                             Integer ano,
                             TipoDePaciente tipoDePaciente,
                             List<LocalDate> datasDasSessoes) {
        validarTipoEData(tipoDePaciente, datasDasSessoes);
        Paciente pacienteObtido = pacienteRepositorio.buscar(nome, usuarioId);
        if (Objects.nonNull(pacienteObtido)) {
            if (!verificarSePacienteJahPossuiValorNoMesEAno(mes, ano, pacienteObtido)) {
                Valor novoValorASerInserido = criarValor(datasDasSessoes, valorPorSessao, mes, ano, tipoDePaciente);
                List<Valor> valores = new ArrayList<>(pacienteObtido.getValores());
                valores.add(novoValorASerInserido);
                pacienteObtido.alterar(valores);

                return pacienteObtido;
            } else {
                throw new ExcecaoDeRegraDeNegocio(String.format("Paciente já possui valores referente ao mês %s e ano %s.", mes, ano));
            }
        } else {
            Valor novoValorASerInserido = criarValor(datasDasSessoes, valorPorSessao, mes, ano, tipoDePaciente);

            return new Paciente(usuarioId,
                    nome,
                    endereco,
                    Collections.singletonList(novoValorASerInserido),
                    tipoDePaciente,
                    datasDasSessoes);
        }
    }

    private void validarTipoEData(TipoDePaciente tipoDePaciente, List<LocalDate> datasDasSessoes) {
        if (tipoDePaciente.ehValorPorSessao() && datasDasSessoes.isEmpty()) {
            throw new ExcecaoDeRegraDeNegocio("Não é possível criar Paciente de valor por sessão sem informar as datas das sessões.");
        }
    }

    private boolean verificarSePacienteJahPossuiValorNoMesEAno(Mes mes, Integer ano, Paciente pacienteObtido) {
        return pacienteObtido.getValores().stream().anyMatch(valor -> valor.ehDoMesmo(mes, ano));
    }

    private Valor criarValor(List<LocalDate> datasDasSessoes, Moeda valorPorSessao, Mes mes, Integer ano, TipoDePaciente tipoDePaciente) {
        return new Valor(Quantidade.criar(datasDasSessoes.size()), valorPorSessao, mes, ano, tipoDePaciente);
    }
}
