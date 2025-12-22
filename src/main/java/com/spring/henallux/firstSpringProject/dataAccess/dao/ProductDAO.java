package com.spring.henallux.firstSpringProject.dataAccess.dao;

import com.spring.henallux.firstSpringProject.dataAccess.repository.ProductRepository;
import com.spring.henallux.firstSpringProject.dataAccess.util.ProductConverter;
import com.spring.henallux.firstSpringProject.model.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ProductDAO implements ProductDataAccess {

    private final ProductRepository repo;
    private final ProductConverter converter;
    private final String imagesPath;

    public ProductDAO(ProductRepository repo, ProductConverter converter) {
        this.repo = repo;
        this.converter = converter;
        this.imagesPath = "C:/Documents/Programation/bookStore/BookStore-main/images";
    }

    @Override
    public List<Product> getAll() {
        return repo.findAll().stream().map(converter::toModel).collect(Collectors.toList());
    }

    @Override
    public List<Product> getByCategory(Integer categoryId) {
        return repo.findByCategoryIdOrderByNameAsc(categoryId)
                .stream().map(converter::toModel).collect(Collectors.toList());
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
