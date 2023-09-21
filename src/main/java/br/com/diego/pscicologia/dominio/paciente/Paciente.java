package br.com.diego.pscicologia.dominio.paciente;

import br.com.diego.pscicologia.comum.Entidade;
import br.com.diego.pscicologia.comum.ExcecaoDeCampoObrigatorio;
import br.com.diego.pscicologia.comum.ExcecaoDeRegraDeNegocio;
import org.springframework.data.annotation.PersistenceCreator;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class Paciente extends Entidade {

    private String nome;
    private String endereco;
    private LocalDate dataDeInicio;
    private List<Valor> valores;
    private Boolean inativo;
    private Tipo tipo;

    @PersistenceCreator
    public Paciente(String nome,
                    String endereco,
                    List<Valor> valores,
                    Tipo tipo) {
        validarCamposObrigatorios(nome, endereco, valores, tipo);
        this.nome = nome;
        this.endereco = endereco;
        this.dataDeInicio = LocalDate.now();
        this.valores = valores;
        this.inativo = false;
        this.tipo = tipo;
    }

    public void alterar(String endereco) {
        validarCamposObrigatorios(endereco);
        this.endereco = endereco;
    }

    private void validarCamposObrigatorios(String nome,
                                           String endereco,
                                           List<Valor> valor,
                                           Tipo tipo) {
        new ExcecaoDeCampoObrigatorio()
                .quandoNulo(nome, "Não é possível criar um paciente sem informar o nome.")
                .quandoNulo(endereco, "Não é possível criar um paciente sem informar o endereço.")
                .quandoColecaoVazia(valor, "Não é possível criar um paciente sem informar o valor.")
                .quandoNulo(tipo, "Não é possível criar um paciente sem informar o tipo.")
                .entaoDispara();
    }

    private void validarCamposObrigatorios(String endereco) {
        new ExcecaoDeCampoObrigatorio()
                .quandoNulo(endereco, "Não é possível alterar um paciente sem informar o endereço.")
                .entaoDispara();
    }

    public void inativar() {
        if (this.getInativo().equals(Boolean.TRUE)) {
            throw new ExcecaoDeRegraDeNegocio("O paciente já está inativo.");
        }
        this.inativo = true;
    }

    public void ativar() {
        if (this.getInativo().equals(Boolean.FALSE)) {
            throw new ExcecaoDeRegraDeNegocio("O paciente já está ativo.");
        }
        this.inativo = false;
    }

    public void alterar(List<Valor> valores) {
        validarCampoObrigatorio(valores);
        this.valores = valores;
    }

    private void validarCampoObrigatorio(List<Valor> valores) {
        new ExcecaoDeCampoObrigatorio()
                .quandoColecaoVazia(valores, "Não é possível adicionar um valor ao paciente, pois, não foi informado os valores.")
                .entaoDispara();
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public LocalDate getDataDeInicio() {
        return dataDeInicio;
    }

    public Boolean getInativo() {
        return inativo;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public List<Valor> getValores() {
        return Collections.unmodifiableList(valores);
    }

    public String obterDescricaoDoTipo() {
        return getTipo().getDescricao();
    }
}
