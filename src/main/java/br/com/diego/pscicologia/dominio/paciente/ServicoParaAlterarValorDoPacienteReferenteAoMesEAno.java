package br.com.diego.pscicologia.dominio.paciente;

import br.com.diego.pscicologia.comum.ExcecaoDeCampoObrigatorio;
import br.com.diego.pscicologia.comum.ExcecaoDeRegraDeNegocio;
import br.com.diego.pscicologia.comum.Mes;
import br.com.diego.pscicologia.comum.Moeda;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServicoParaAlterarValorDoPacienteReferenteAoMesEAno {


    public List<Valor> alterar(List<Valor> valoresAtuais,
                               Moeda novoValorPorSessao,
                               Mes mes,
                               Integer ano,
                               TipoDePaciente tipoDePaciente,
                               List<LocalDate> novasDatasDasSessoes) {
        validarCamposObrigatorios(valoresAtuais, novoValorPorSessao, mes, ano, tipoDePaciente);
        List<Valor> valoresAlterados = new ArrayList<>(valoresAtuais);
        boolean valorRemovido = valoresAlterados.removeIf(valor -> valor.ehDoMesmo(mes, ano));
        validarValorRemovido(mes, ano, valorRemovido);
        Valor novoValorDoMes = new Valor(novoValorPorSessao, mes, ano, tipoDePaciente, novasDatasDasSessoes);
        valoresAlterados.add(novoValorDoMes);
        valoresAtuais = valoresAlterados;

        return valoresAtuais;
    }

    private static void validarValorRemovido(Mes mes, Integer ano, boolean valorRemovido) {
        if (!valorRemovido) {
            throw  new ExcecaoDeRegraDeNegocio(String.format("Não foi possível encontrar o valor do mês %s e ano %s para alterar", mes, ano));
        }
    }

    private void validarCamposObrigatorios(List<Valor> valoresAtuais,
                                           Moeda novoValorPorSessao,
                                           Mes mes,
                                           Integer ano,
                                           TipoDePaciente tipoDePaciente) {
        new ExcecaoDeCampoObrigatorio()
                .quandoColecaoVazia(valoresAtuais, "Não é possível alterar um valor sem informar os valores atuais do mês e ano.")
                .quandoNulo(novoValorPorSessao, "Não é possível alterar um valor sem informar o novo valor por sessão.")
                .quandoNulo(mes, "Não é possível alterar um valor sem informar o mês.")
                .quandoNulo(ano, "Não é possível alterar um valor sem informar o ano.")
                .quandoNulo(tipoDePaciente, "Não é possível alterar um valor sem informar o tipo.")
                .entaoDispara();
    }
}
