package br.com.diego.pscicologia.builder;

import br.com.diego.pscicologia.dominio.paciente.Paciente;
import br.com.diego.pscicologia.dominio.paciente.Tipo;
import br.com.diego.pscicologia.dominio.paciente.Valor;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class PacienteBuilder {

    private String nome;
    private String endereco;
    private LocalDate dataDeInicio;
    private List<Valor> valores;
    private Boolean inativo;
    private Tipo tipo;

    public PacienteBuilder() {
        this.nome = "Diego Guedes";
        this.endereco = "Rua Batatinha, Bairro das batatas";
        this.dataDeInicio = LocalDate.now();
        this.valores = Collections.singletonList(new ValorBuilder().criar());
        this.inativo = false;
        this.tipo = Tipo.VALOR_POR_SESSAO;
    }

    public Paciente criar() {
        Paciente paciente = new Paciente(nome, endereco, valores, tipo);
        inativar(paciente);
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

    public PacienteBuilder comValores(List<Valor> valores) {
        this.valores = valores;
        return this;
    }

    public PacienteBuilder comTipo(Tipo tipo) {
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

    private void inativar(Paciente paciente) {
        if (this.inativo.equals(Boolean.TRUE)) {
            paciente.inativar();
        }
    }
}
