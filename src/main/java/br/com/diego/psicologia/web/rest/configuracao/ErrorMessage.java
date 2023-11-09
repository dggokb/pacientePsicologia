package br.com.diego.psicologia.web.rest.configuracao;

import java.time.LocalDate;

public record ErrorMessage(LocalDate currentDate, String massage) {
}
