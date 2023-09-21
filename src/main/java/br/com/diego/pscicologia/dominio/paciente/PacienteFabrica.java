package br.com.diego.pscicologia.dominio.paciente;

import br.com.diego.pscicologia.comum.ExcecaoDeRegraDeNegocio;
import br.com.diego.pscicologia.comum.Mes;
import br.com.diego.pscicologia.comum.Moeda;
import br.com.diego.pscicologia.comum.Quantidade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PacienteFabrica {

    private final PacienteRepositorio pacienteRepositorio;

    public PacienteFabrica(PacienteRepositorio pacienteRepositorio) {
        this.pacienteRepositorio = pacienteRepositorio;
    }

    public Paciente fabricar(String nome,
                             String endereco,
                             Quantidade quantidaDeDiasNoMes,
                             Moeda valorPorSessao,
                             Mes mes,
                             Integer ano,
                             Tipo tipo) {

        Paciente pacienteObtido = pacienteRepositorio.buscar(nome);
        if (Objects.nonNull(pacienteObtido)) {
            if (!verificarSePacienteJahPossuiValorNoMesEAno(mes, ano, pacienteObtido)) {
                Valor novoValorASerInserido = criarValor(quantidaDeDiasNoMes, valorPorSessao, mes, ano, tipo);
                List<Valor> valores = new ArrayList<>(pacienteObtido.getValores());
                valores.add(novoValorASerInserido);
                pacienteObtido.alterar(valores);
                return pacienteObtido;
            } else {
                throw new ExcecaoDeRegraDeNegocio(String.format("Paciente já possui valores referente ao mês %s e ano %s.", mes, ano));
            }
        } else {
            Valor novoValorASerInserido = criarValor(quantidaDeDiasNoMes, valorPorSessao, mes, ano, tipo);

            return new Paciente(nome, endereco, Collections.singletonList(novoValorASerInserido), tipo);
        }
    }

    private boolean verificarSePacienteJahPossuiValorNoMesEAno(Mes mes, Integer ano, Paciente pacienteObtido) {
        return pacienteObtido.getValores().stream().anyMatch(valor -> valor.ehDoMesmo(mes, ano));
    }

    private static Valor criarValor(Quantidade quantidaDeDiasNoMes, Moeda valorPorSessao, Mes mes, Integer ano, Tipo tipo) {
        return new Valor(quantidaDeDiasNoMes, valorPorSessao, mes, ano, tipo);
    }
}
