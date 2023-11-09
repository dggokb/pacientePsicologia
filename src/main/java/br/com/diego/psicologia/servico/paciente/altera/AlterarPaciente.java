package br.com.diego.psicologia.servico.paciente.altera;

import br.com.diego.psicologia.comum.*;
import br.com.diego.psicologia.dominio.paciente.TipoDePaciente;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AlterarPaciente implements Comando {

    private String id;
    private String nome;
    private String endereco;
    private Moeda valorPorSessao;
    private Mes mes;
    private Integer ano;
    private TipoDePaciente tipoDePaciente;
    private List<LocalDate> datasDasSessoes = new ArrayList<>();

    public AlterarPaciente(String id,
                           String nome,
                           String endereco,
                           BigDecimal valorPorSessao,
                           String mes,
                           Integer ano,
                           String tipo,
                           List<Date> datasDasSessoes) {
        this.id = id;
        this.tipoDePaciente = TipoDePaciente.valueOf(tipo);
        this.nome = nome;
        this.endereco = endereco;
        this.valorPorSessao = Moeda.criar(valorPorSessao);
        validarTipoEData(this.tipoDePaciente, datasDasSessoes);
        this.datasDasSessoes = criarDatas(datasDasSessoes);
        this.mes = tipoDePaciente.ehValorPorSessao() ? Mes.valueOf(DateUtils.obterMesPorExtenso(this.datasDasSessoes.get(0)).toUpperCase()) : Mes.valueOf(mes);
        this.ano = tipoDePaciente.ehValorPorSessao() ? this.datasDasSessoes.get(0).getYear() : ano;
    }

    private List<LocalDate> criarDatas(List<Date> datasDasSessoes) {
        return datasDasSessoes.stream().map(DateUtils::converteDateToLocalDate).collect(Collectors.toList());
    }

    private void validarTipoEData(TipoDePaciente tipoDePaciente, List<Date> datasDasSessoes) {
        if (tipoDePaciente.ehValorPorSessao() && datasDasSessoes.isEmpty()) {
            throw new ExcecaoDeRegraDeNegocio("Não é possível alterar o Paciente de valor por sessão sem informar as datas das sessões.");
        }
    }

    public String getId() {
        return id;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getNome() {
        return nome;
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

    public TipoDePaciente getTipoDePaciente() {
        return tipoDePaciente;
    }

    public List<LocalDate> getDatasDasSessoes() {
        return datasDasSessoes;
    }
}
