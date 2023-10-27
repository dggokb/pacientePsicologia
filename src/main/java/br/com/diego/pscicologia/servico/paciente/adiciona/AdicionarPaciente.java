package br.com.diego.pscicologia.servico.paciente.adiciona;

import br.com.diego.pscicologia.comum.Comando;
import br.com.diego.pscicologia.comum.Mes;
import br.com.diego.pscicologia.comum.Moeda;
import br.com.diego.pscicologia.comum.Quantidade;
import br.com.diego.pscicologia.dominio.paciente.TipoDePaciente;

import java.math.BigDecimal;
import java.util.Optional;

public class AdicionarPaciente implements Comando {

    private String usuarioId;
    private String nome;
    private String endereco;
    private Quantidade quantidadeDeDiasNoMes;
    private Moeda valorPorSessao;
    private Mes mes;
    private Integer ano;
    private TipoDePaciente tipoDePaciente;

    public AdicionarPaciente(String usuarioId,
                             String nome,
                             String endereco,
                             Optional<Integer> quantidadeDeDiasNoMes,
                             BigDecimal valorPorSessao,
                             String mes,
                             Integer ano,
                             String tipo) {
        this.usuarioId = usuarioId;
        this.nome = nome;
        this.endereco = endereco;
        this.quantidadeDeDiasNoMes = quantidadeDeDiasNoMes.map(Quantidade::criar).orElse(null);
        this.valorPorSessao = Moeda.criar(valorPorSessao);
        this.mes = Mes.valueOf(mes);
        this.ano = ano;
        this.tipoDePaciente = TipoDePaciente.valueOf(tipo);
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

    public TipoDePaciente getTipo() {
        return tipoDePaciente;
    }
}
