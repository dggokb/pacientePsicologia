package br.com.diego.pscicologia.builder;

import br.com.diego.pscicologia.comum.Moeda;
import br.com.diego.pscicologia.comum.Quantidade;
import br.com.diego.pscicologia.dominio.paciente.Paciente;
import br.com.diego.pscicologia.dominio.paciente.Tipo;

import java.time.LocalDate;

public class PacienteBuilder {

    private String nome;
    private String endereco;
    private LocalDate dataDeInicio;
    private Quantidade quantidaDeDiasNoMes;
    private Moeda valorPorSessao;
    private Boolean inativo;
    private Tipo tipo;

    public PacienteBuilder() {
        this.nome = "Diego Guedes";
        this.endereco = "Rua Batatinha, Bairro das batatas";
        this.dataDeInicio = LocalDate.now();
        this.quantidaDeDiasNoMes = Quantidade.criar(2);
        this.valorPorSessao = Moeda.criar(23);
        this.inativo = false;
        this.tipo = Tipo.VALOR_MENSAL;
    }

    public Paciente criar() {
        Paciente paciente = new Paciente(nome, endereco, quantidaDeDiasNoMes, valorPorSessao, tipo);
        if (this.inativo.equals(Boolean.TRUE)) {
            paciente.inativar();
        }
        return paciente;
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

    public PacienteBuilder comTipoDePaciente(Tipo tipo) {
        this.tipo = tipo;
        return this;
    }

    public PacienteBuilder ativo() {
        this.inativo = false;
        return this;
    }

    public PacienteBuilder inativo() {
        this.inativo = true;
        return this;
    }
}
