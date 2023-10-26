package br.com.diego.pscicologia.web.rest.configuracao;

import java.time.LocalDate;

public record ErrorMessage(LocalDate currentDate, String massage) {
}
