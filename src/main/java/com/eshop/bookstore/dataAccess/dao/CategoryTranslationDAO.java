package com.eshop.bookstore.dataAccess.dao;

import com.eshop.bookstore.dataAccess.repository.TranslationRepository;
import com.eshop.bookstore.model.CategoryLabel;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(Transactional.TxType.SUPPORTS)
public class CategoryTranslationDAO implements CategoryTranslationDataAccess {

    private static final String ENTITY_TYPE = "category";

    private final TranslationRepository translations;

    public CategoryTranslationDAO(TranslationRepository translations) {
        this.translations = translations;
    }

    @Override
    public List<CategoryLabel> listCategoriesWithNames() {
        String languageId = LocaleContextHolder.getLocale().getLanguage();
        return translations.findByIdLanguageIdAndIdEntityType(languageId, ENTITY_TYPE).stream()
                .map(t -> new CategoryLabel(t.getId().getEntityId(), t.getName()))
                .collect(Collectors.toList());
    }
}
