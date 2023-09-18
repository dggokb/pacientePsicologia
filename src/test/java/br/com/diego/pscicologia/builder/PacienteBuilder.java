package br.com.diego.pscicologia.builder;

import br.com.diego.pscicologia.comum.Moeda;
import br.com.diego.pscicologia.comum.Quantidade;
import br.com.diego.pscicologia.dominio.paciente.Paciente;

import java.time.LocalDate;
import java.util.UUID;

public class PacienteBuilder {

    private String nome;
    private String endereco;
    private LocalDate dataDeInicio;
    private Quantidade quantidaDeDiasNoMes;
    private Moeda valorPorSessao;

    public PacienteBuilder() {
        this.nome = "Diego Guedes";
        this.endereco = "Rua Batatinha, Bairro das batatas";
        this.dataDeInicio = LocalDate.now();
        this.quantidaDeDiasNoMes = Quantidade.criar(2);
        this.valorPorSessao = Moeda.criar(23);
    }

    public Paciente criar() {
        return new Paciente(nome, endereco, quantidaDeDiasNoMes, valorPorSessao);
    }

    public PacienteBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public PacienteBuilder comEndereco(String endereco) {
        this.endereco = endereco;
        return this;
    }

    public PacienteBuilder comDataDeInicio(LocalDate dataDeInicio) {
        this.dataDeInicio = dataDeInicio;
        return this;
    }

    public PacienteBuilder comQuantidaDeDiasNoMes(Quantidade quantidaDeDiasNoMes) {
        this.quantidaDeDiasNoMes = quantidaDeDiasNoMes;
        return this;
    }

    public PacienteBuilder comValorPorSessao(Moeda valorPorSessao) {
        this.valorPorSessao = valorPorSessao;
        return this;
    }
}
