package com.eshop.bookstore.dataAccess.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TranslationId implements Serializable {
    @Column(name = "language_id", length = 5, nullable = false)
    private String languageId;

    @Column(name = "entity_type", length = 20, nullable = false)
    private String entityType;

    @Column(name = "entity_id", nullable = false)
    private Integer entityId;

    protected TranslationId() {}

    public TranslationId(String languageId, String entityType, Integer entityId) {
        this.languageId = languageId;
        this.entityType = entityType;
        this.entityId = entityId;
    }

    public String getLanguageId() { return languageId; }
    public String getEntityType() { return entityType; }
    public Integer getEntityId() { return entityId; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TranslationId)) return false;
        TranslationId that = (TranslationId) o;
        return Objects.equals(languageId, that.languageId)
                && Objects.equals(entityType, that.entityType)
                && Objects.equals(entityId, that.entityId);
    }
    @Override public int hashCode() { return Objects.hash(languageId, entityType, entityId); }
}
