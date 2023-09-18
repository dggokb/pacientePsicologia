package br.com.diego.pscicologia.dominio.paciente;

import br.com.diego.pscicologia.comum.Entidade;
import br.com.diego.pscicologia.comum.ExcecaoDeCampoObrigatorio;
import br.com.diego.pscicologia.comum.Moeda;
import br.com.diego.pscicologia.comum.Quantidade;

import java.time.LocalDate;
import java.util.UUID;

public class Paciente extends Entidade {

    private String nome;
    private String endereco;
    private LocalDate dataDeInicio;
    private Quantidade quantidaDeDiasNoMes;
    private Moeda valorPorSessao;

    public Paciente(String nome,
                    String endereco,
                    Quantidade quantidaDeDiasNoMes,
                    Moeda valorPorSessao) {
        validarCamposObrigatorios(nome, endereco, quantidaDeDiasNoMes, valorPorSessao);
        this.nome = nome;
        this.endereco = endereco;
        this.dataDeInicio = LocalDate.now();
        this.quantidaDeDiasNoMes = quantidaDeDiasNoMes;
        this.valorPorSessao = valorPorSessao;
    }

    private void validarCamposObrigatorios(String nome,
                                           String endereco,
                                           Quantidade quantidaDeDiasNoMes,
                                           Moeda valorPorSessao) {
        new ExcecaoDeCampoObrigatorio()
                .quandoNulo(nome, "Não é possível criar um paciênte sem informar o nome.")
                .quandoNulo(endereco, "Não é possível criar um paciênte sem informar o endereço.")
                .quandoNulo(quantidaDeDiasNoMes, "Não é possível criar um paciênte sem quantidade de dias no mes.")
                .quandoNulo(valorPorSessao, "Não é possível criar um paciênte sem informar o valor por sessão.")
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

    public Quantidade getQuantidaDeDiasNoMes() {
        return quantidaDeDiasNoMes;
    }

    public Moeda getValorPorSessao() {
        return valorPorSessao;
    }
}
