package br.com.diego.pscicologia.servico.paciente.adiciona;

import br.com.diego.pscicologia.comum.Comando;
import br.com.diego.pscicologia.comum.Moeda;
import br.com.diego.pscicologia.comum.Quantidade;
import br.com.diego.pscicologia.dominio.paciente.Tipo;

import java.math.BigDecimal;

public class AdicionarPaciente implements Comando {

    private String nome;
    private String endereco;
    private Quantidade quantidaDeDiasNoMes;
    private Moeda valorPorSessao;
    private Tipo tipo;

    public AdicionarPaciente(String nome,
                             String endereco,
                             Integer quantidaDeDiasNoMes,
                             BigDecimal valorPorSessao,
                             String tipo) {
        this.nome = nome;
        this.endereco = endereco;
        this.quantidaDeDiasNoMes = Quantidade.criar(quantidaDeDiasNoMes);
        this.valorPorSessao = Moeda.criar(valorPorSessao);
        this.tipo = Tipo.valueOf(tipo);
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

    public Tipo getTipo() {
        return tipo;
    }
}
