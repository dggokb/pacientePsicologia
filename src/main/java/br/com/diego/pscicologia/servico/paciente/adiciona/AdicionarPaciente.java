package br.com.diego.pscicologia.servico.paciente.adiciona;

import br.com.diego.pscicologia.comum.*;
import br.com.diego.pscicologia.dominio.paciente.TipoDePaciente;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AdicionarPaciente implements Comando {

    private String usuarioId;
    private String nome;
    private String endereco;
    private Quantidade quantidadeDeDiasNoMes;
    private Moeda valorPorSessao;
    private Mes mes;
    private Integer ano;
    private TipoDePaciente tipoDePaciente;
    private List<LocalDate> datasDasSessoes;

    public AdicionarPaciente(String usuarioId,
                             String nome,
                             String endereco,
                             Optional<Integer> quantidadeDeDiasNoMes,
                             BigDecimal valorPorSessao,
                             String mes,
                             Integer ano,
                             String tipo,
                             List<Date> datasDasSessoes) {
        this.usuarioId = usuarioId;
        this.nome = nome;
        this.endereco = endereco;
        this.quantidadeDeDiasNoMes = quantidadeDeDiasNoMes.map(Quantidade::criar).orElse(null);
        this.valorPorSessao = Moeda.criar(valorPorSessao);
        this.mes = Mes.valueOf(mes);
        this.ano = ano;
        this.tipoDePaciente = TipoDePaciente.valueOf(tipo);
        this.datasDasSessoes = criarDatas(datasDasSessoes);
    }

    private List<LocalDate> criarDatas(List<Date> datasDasSessoes) {
        return datasDasSessoes.stream().map(DateUtils::converteDateToLocalDate).collect(Collectors.toList());
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

    public List<LocalDate> getDatasDasSessoes() {
        return datasDasSessoes;
    }
}
