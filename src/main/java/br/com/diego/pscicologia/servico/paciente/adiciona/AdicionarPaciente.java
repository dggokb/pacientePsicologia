package br.com.diego.pscicologia.servico.paciente.adiciona;

import br.com.diego.pscicologia.comum.Comando;
import br.com.diego.pscicologia.comum.Moeda;
import br.com.diego.pscicologia.comum.Quantidade;

import java.math.BigDecimal;

public class AdicionarPaciente implements Comando {

    private String nome;
    private String endereco;
    private Quantidade quantidaDeDiasNoMes;
    private Moeda valorPorSessao;

    public AdicionarPaciente(String nome, String endereco, Integer quantidaDeDiasNoMes, BigDecimal valorPorSessao) {
        this.nome = nome;
        this.endereco = endereco;
        this.quantidaDeDiasNoMes = Quantidade.criar(quantidaDeDiasNoMes);
        this.valorPorSessao = Moeda.criar(valorPorSessao);
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public Quantidade getQuantidaDeDiasNoMes() {
        return quantidaDeDiasNoMes;
    }

    public Moeda getValorPorSessao() {
        return valorPorSessao;
    }
}
