package br.com.diego.pscicologia.servico.paciente.altera;

import br.com.diego.pscicologia.comum.Comando;
import br.com.diego.pscicologia.comum.Moeda;

import java.math.BigDecimal;

public class AlterarPaciente implements Comando {

    private String id;
    private String endereco;

    public AlterarPaciente(String id, String endereco) {
        this.id = id;
        this.endereco = endereco;
    }

    public String getId() {
        return id;
    }

    public String getEndereco() {
        return endereco;
    }
}
