package br.com.diego.pscicologia.dominio.usuario;

import br.com.diego.pscicologia.comum.Entidade;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

public class RefreshToken extends Entidade {
    @DocumentReference
    private Usuario usuario;
}
