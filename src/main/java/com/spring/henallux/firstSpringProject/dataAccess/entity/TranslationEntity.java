package com.spring.henallux.firstSpringProject.dataAccess.entity;

import javax.persistence.*;

@Entity
@Table(name = "translation")
public class TranslationEntity {

    @EmbeddedId
    private TranslationId id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description")
    private String description;

    protected TranslationEntity() {}

    public TranslationEntity(TranslationId id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public TranslationId getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
}
