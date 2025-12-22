package com.spring.henallux.firstSpringProject.dataAccess.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class TranslationId implements Serializable {
    @Column(name = "language_id", length = 5, nullable = false)
    private String languageId;

    @Column(name = "category_id", nullable = false)
    private Integer categoryId;

    protected TranslationId() {}

    public TranslationId(String languageId, Integer categoryId) {
        this.languageId = languageId;
        this.categoryId = categoryId;
    }

    public String getLanguageId() { return languageId; }
    public Integer getCategoryId() { return categoryId; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TranslationId)) return false;
        TranslationId that = (TranslationId) o;
        return Objects.equals(languageId, that.languageId)
                && Objects.equals(categoryId, that.categoryId);
    }
    @Override public int hashCode() { return Objects.hash(languageId, categoryId); }
}
