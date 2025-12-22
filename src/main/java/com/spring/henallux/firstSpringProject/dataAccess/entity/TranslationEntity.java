package com.spring.henallux.firstSpringProject.dataAccess.entity;

import javax.persistence.*;

@Entity
@Table(name = "Translation")
public class TranslationEntity {

    @EmbeddedId
    private TranslationId id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    protected TranslationEntity() {}

    public TranslationEntity(TranslationId id, String name) {
        this.id = id;
        this.name = name;
    }

    public TranslationId getId() { return id; }
    public String getName() { return name; }
}
