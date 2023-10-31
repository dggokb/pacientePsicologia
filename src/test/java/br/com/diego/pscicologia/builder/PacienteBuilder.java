package br.com.diego.pscicologia.builder;

import br.com.diego.pscicologia.dominio.paciente.Paciente;
import br.com.diego.pscicologia.dominio.paciente.TipoDePaciente;
import br.com.diego.pscicologia.dominio.paciente.Valor;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class PacienteBuilder {

    private String usuarioId;
    private String nome;
    private String endereco;
    private LocalDate dataDeInicio;
    private List<Valor> valores;
    private Boolean inativo;
    private TipoDePaciente tipoDePaciente;
    private List<LocalDate> datasDasSessoes;

    public PacienteBuilder() {
        this.usuarioId = UUID.randomUUID().toString();
        this.nome = "Diego Guedes";
        this.endereco = "Rua Batatinha, Bairro das batatas";
        this.dataDeInicio = LocalDate.now();
        this.valores = Collections.singletonList(new ValorBuilder().criar());
        this.inativo = false;
        this.tipoDePaciente = TipoDePaciente.VALOR_POR_SESSAO;
        this.datasDasSessoes = Collections.singletonList(LocalDate.now());
    }

    public Paciente criar() {
        Paciente paciente = new Paciente(usuarioId, nome, endereco, valores, tipoDePaciente, datasDasSessoes);
        inativar(paciente);
        return paciente;
    }

    public PacienteBuilder comUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
        return this;
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

    public PacienteBuilder comValores(Valor... valores) {
        this.valores = List.of(valores);
        return this;
    }

    public PacienteBuilder comTipo(TipoDePaciente tipoDePaciente) {
        this.tipoDePaciente = tipoDePaciente;
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
