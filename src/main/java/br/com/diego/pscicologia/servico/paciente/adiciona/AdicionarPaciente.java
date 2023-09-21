package br.com.diego.pscicologia.servico.paciente.adiciona;

import br.com.diego.pscicologia.comum.Comando;
import br.com.diego.pscicologia.comum.Mes;
import br.com.diego.pscicologia.comum.Moeda;
import br.com.diego.pscicologia.comum.Quantidade;
import br.com.diego.pscicologia.dominio.paciente.Tipo;

import java.math.BigDecimal;
import java.util.Optional;

public class AdicionarPaciente implements Comando {

    private String nome;
    private String endereco;
    private Quantidade quantidadeDeDiasNoMes;
    private Moeda valorPorSessao;
    private Mes mes;
    private Integer ano;
    private Tipo tipo;

    public AdicionarPaciente(String nome,
                             String endereco,
                             Optional<Integer> quantidadeDeDiasNoMes,
                             BigDecimal valorPorSessao,
                             String mes,
                             Integer ano,
                             String tipo) {
        this.nome = nome;
        this.endereco = endereco;
        this.quantidadeDeDiasNoMes = quantidadeDeDiasNoMes.map(Quantidade::criar).orElse(null);
        this.valorPorSessao = Moeda.criar(valorPorSessao);
        this.mes = Mes.valueOf(mes);
        this.ano = ano;
        this.tipo = Tipo.valueOf(tipo);
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public Quantidade getQuantidadeDeDiasNoMes() {
        return quantidadeDeDiasNoMes;
    }

    public Moeda getValorPorSessao() {
        return valorPorSessao;
    }

    public Mes getMes() {
        return mes;
    }

    public Integer getAno() {
        return ano;
    }

    public Tipo getTipo() {
        return tipo;
    }
}
