package br.com.diego.pscicologia.servico.paciente.altera;

import br.com.diego.pscicologia.comum.Comando;
import br.com.diego.pscicologia.comum.Moeda;

import java.math.BigDecimal;

public class AlterarPaciente implements Comando {

    private String id;
    private String endereco;
    private Moeda valorPorSessao;

    public AlterarPaciente(String id, String endereco, BigDecimal valorPorSessao) {
        this.id = id;
        this.endereco = endereco;
        this.valorPorSessao = Moeda.criar(valorPorSessao);
    }

    public String getId() {
        return id;
    }

    public String getEndereco() {
        return endereco;
    }

    public Moeda getValorPorSessao() {
        return valorPorSessao;
    }
}
