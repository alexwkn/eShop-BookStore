package com.eshop.bookstore.dataAccess.repository;

import com.eshop.bookstore.dataAccess.entity.TranslationEntity;
import com.eshop.bookstore.dataAccess.entity.TranslationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TranslationRepository extends JpaRepository<TranslationEntity, TranslationId> {

    List<TranslationEntity> findByIdLanguageIdAndIdEntityType(String languageId, String entityType);
}
