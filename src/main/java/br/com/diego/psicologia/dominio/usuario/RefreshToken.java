package br.com.diego.psicologia.dominio.usuario;

import br.com.diego.psicologia.comum.Entidade;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

public class RefreshToken extends Entidade {
    @DocumentReference
    private Usuario usuario;
}
