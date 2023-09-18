package br.com.diego.pscicologia.comum;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document
public class Entidade {

    @Id
    private String id;

    public Entidade() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }
}
