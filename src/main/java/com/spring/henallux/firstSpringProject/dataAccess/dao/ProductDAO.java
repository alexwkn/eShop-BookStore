package com.spring.henallux.firstSpringProject.dataAccess.dao;

import com.spring.henallux.firstSpringProject.dataAccess.entity.TranslationEntity;
import com.spring.henallux.firstSpringProject.dataAccess.repository.ProductRepository;
import com.spring.henallux.firstSpringProject.dataAccess.repository.TranslationRepository;
import com.spring.henallux.firstSpringProject.dataAccess.util.ProductConverter;
import com.spring.henallux.firstSpringProject.model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ProductDAO implements ProductDataAccess {

    private static final String ENTITY_TYPE = "product";

    private final ProductRepository repo;
    private final TranslationRepository translationRepo;
    private final ProductConverter converter;
    private final String imagesPath;

    public ProductDAO(ProductRepository repo, TranslationRepository translationRepo, ProductConverter converter) {
        this.repo = repo;
        this.translationRepo = translationRepo;
        this.converter = converter;
        this.imagesPath = Paths.get("images").toAbsolutePath().toString();

    }

    private Map<Integer, TranslationEntity> translationsForCurrentLocale() {
        String languageId = LocaleContextHolder.getLocale().getLanguage();
        return translationRepo.findByIdLanguageIdAndIdEntityType(languageId, ENTITY_TYPE).stream()
                .collect(Collectors.toMap(t -> t.getId().getEntityId(), Function.identity()));
    }

    private Product translate(Product p, Map<Integer, TranslationEntity> translations) {
        TranslationEntity t = translations.get(p.getId());
        if (t == null) return p;
        return new Product(p.getId(), t.getName(), p.getPrice(),
                t.getDescription() != null ? t.getDescription() : p.getDescription(),
                p.getCategoryId(), p.getAuthor());
    }

    @Override
    public List<Product> getAll() {
        Map<Integer, TranslationEntity> translations = translationsForCurrentLocale();
        return repo.findAll().stream().map(converter::toModel)
                .map(p -> translate(p, translations)).collect(Collectors.toList());
    }

    @Override
    public List<Product> getByCategory(Integer categoryId) {
        Map<Integer, TranslationEntity> translations = translationsForCurrentLocale();
        return repo.findByCategoryIdOrderByNameAsc(categoryId)
                .stream().map(converter::toModel)
                .map(p -> translate(p, translations)).collect(Collectors.toList());
    }

    public Optional<Resource> getImage(Integer productId) {

        return repo.findById(productId)
                .flatMap(entity -> {
                    // On récupère le chemin stocké en DB
                    String imagePath = entity.getImagePath();

                    if (imagePath == null || imagePath.isBlank()) {
                        return Optional.empty();
                    }

                    // Construction sécurisée du chemin
                    Path path = Paths.get(imagesPath)
                            .resolve(imagePath)
                            .normalize();

                    Resource resource = new FileSystemResource(path);

                    return resource.exists()
                            ? Optional.of(resource)
                            : Optional.empty();
                });
    }

}
