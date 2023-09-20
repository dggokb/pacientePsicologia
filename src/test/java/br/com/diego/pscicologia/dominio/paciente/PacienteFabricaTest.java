package br.com.diego.pscicologia.dominio.paciente;

import br.com.diego.pscicologia.comum.Moeda;
import br.com.diego.pscicologia.comum.Quantidade;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PacienteFabricaTest {

    private PacienteFabrica fabrica;
    private String nome;
    private String endereco;
    private Quantidade quantidadeDeDiasNoMes;
    private Moeda valorPorSessao;

    @BeforeEach
    void setUp() {
        nome = "Diego";
        endereco = "Av lalal lulu";
        quantidadeDeDiasNoMes = Quantidade.criar(10);
        valorPorSessao = Moeda.criar(100);
        fabrica = new PacienteFabrica();
    }

    @Test
    void deveFabricarUmPacienteDoTipoValorPorSessaoComQuantidadeDeDiasNoMesQuandoForDoTipoValorPoSessao() {
        Tipo tipoEsperado = Tipo.VALOR_POR_SESSAO;
        Quantidade quantidadeDeDiasNoMesEsperado = Quantidade.criar(10);

        Paciente pacienteFabricado = fabrica.fabricar(nome, endereco, quantidadeDeDiasNoMesEsperado, valorPorSessao, tipoEsperado);

        Assertions.assertThat(pacienteFabricado.getTipo()).isEqualTo(tipoEsperado);
        Assertions.assertThat(pacienteFabricado.getQuantidadeDeDiasNoMes()).isEqualTo(quantidadeDeDiasNoMesEsperado);
    }

    @Test
    void deveFabricarUmPacienteDoTipoValorFixoSemQuantidadeDeDiasNoMesQuandoForDoTipoFixo() {
        Tipo tipoEsperado = Tipo.VALOR_FIXO;

        Paciente pacienteFabricado = fabrica.fabricar(nome, endereco, quantidadeDeDiasNoMes, valorPorSessao, tipoEsperado);

        Assertions.assertThat(pacienteFabricado.getTipo()).isEqualTo(tipoEsperado);
        Assertions.assertThat(pacienteFabricado.getQuantidadeDeDiasNoMes()).isNull();
    }

    @Test
    void naoDeveFabricarUmPacienteSeNaoForInformadoOTipo() {
        String mensagemEsperada = "É necessário informar o tipo de paciente para criar um paciente.";
        Tipo tipoEsperado = null;

        Throwable excecaoLancada = Assertions.catchThrowable(() -> fabrica.fabricar(nome, endereco, quantidadeDeDiasNoMes, valorPorSessao, tipoEsperado));

        Assertions.assertThat(excecaoLancada).hasMessageContaining(mensagemEsperada);
    }
}