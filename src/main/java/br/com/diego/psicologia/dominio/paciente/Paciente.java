package br.com.diego.psicologia.dominio.paciente;

import br.com.diego.psicologia.comum.Entidade;
import br.com.diego.psicologia.comum.ExcecaoDeCampoObrigatorio;
import br.com.diego.psicologia.comum.ExcecaoDeRegraDeNegocio;
import org.springframework.data.annotation.PersistenceCreator;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class Paciente extends Entidade {

    private String usuarioId;
    private String nome;
    private String endereco;
    private LocalDate dataDeInicio;
    private List<Valor> valores;
    private Boolean inativo;
    private TipoDePaciente tipoDePaciente;

    @PersistenceCreator
    public Paciente(String usuarioId,
                    String nome,
                    String endereco,
                    List<Valor> valores,
                    TipoDePaciente tipoDePaciente) {
        validarCamposObrigatorios(usuarioId, nome, endereco, valores, tipoDePaciente);
        this.usuarioId = usuarioId;
        this.nome = nome;
        this.endereco = endereco;
        this.dataDeInicio = LocalDate.now();
        this.valores = valores;
        this.inativo = false;
        this.tipoDePaciente = tipoDePaciente;
    }

    public void alterar(String nome,
                        String endereco,
                        TipoDePaciente tipoDePaciente,
                        List<Valor> valores) {
        validarCamposObrigatorios(nome, endereco, tipoDePaciente, valores);
        this.nome = nome;
        this.endereco = endereco;
        this.tipoDePaciente = tipoDePaciente;
        this.valores = valores;
    }

    private void validarCamposObrigatorios(String usuarioId,
                                           String nome,
                                           String endereco, List<Valor> valor,
                                           TipoDePaciente tipoDePaciente) {
        new ExcecaoDeCampoObrigatorio()
                .quandoNulo(usuarioId, "Não é possível criar um paciente sem informar o usuário.")
                .quandoNulo(nome, "Não é possível criar um paciente sem informar o nome.")
                .quandoNulo(nome, "Não é possível criar um paciente sem informar o nome.")
                .quandoNulo(endereco, "Não é possível criar um paciente sem informar o endereço.")
                .quandoColecaoVazia(valor, "Não é possível criar um paciente sem informar o valor.")
                .quandoNulo(tipoDePaciente, "Não é possível criar um paciente sem informar o tipo.")
                .entaoDispara();
    }

    private void validarCamposObrigatorios(String nome,
                                           String endereco,
                                           TipoDePaciente tipoDePaciente,
                                           List<Valor> valores) {
        new ExcecaoDeCampoObrigatorio()
                .quandoNulo(nome, "Não é possível alterar um paciente sem informar o nome.")
                .quandoNulo(endereco, "Não é possível alterar um paciente sem informar o endereço.")
                .quandoNulo(tipoDePaciente, "Não é possível alterar um paciente sem informar o tipo.")
                .quandoColecaoVazia(valores, "Não é possível alterar um paciente sem informar os valores das sessões.")
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

    public String getUsuarioId() {
        return usuarioId;
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

    public TipoDePaciente getTipo() {
        return tipoDePaciente;
    }

    public List<Valor> getValores() {
        return Collections.unmodifiableList(valores);
    }

    public String obterDescricaoDoTipo() {
        return getTipo().getDescricao();
    }
}
