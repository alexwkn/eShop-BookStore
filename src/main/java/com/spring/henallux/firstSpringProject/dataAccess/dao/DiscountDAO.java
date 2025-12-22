package com.spring.henallux.firstSpringProject.dataAccess.dao;

import com.spring.henallux.firstSpringProject.dataAccess.entity.DiscountEntity;
import com.spring.henallux.firstSpringProject.dataAccess.repository.DiscountRepository;
import com.spring.henallux.firstSpringProject.dataAccess.util.DiscountConverter;
import com.spring.henallux.firstSpringProject.model.Discount;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DiscountDAO implements DiscountDataAccess {
    private final DiscountRepository repository;
    private final DiscountConverter discountConverter;

    public DiscountDAO(DiscountRepository repository, DiscountConverter discountConverter) {
        this.repository = repository;
        this.discountConverter = discountConverter;
    }

    @Override
    public Discount findByCode(String code) {
        DiscountEntity entity = repository.findByCode(code);
        if (entity == null) return null;
        return discountConverter.discountEntityToModel(entity);
    }

    @Override
    public List<Discount> findAll() {
        return repository.findAll()
                .stream()
                .map(discountConverter::discountEntityToModel)
                .collect(Collectors.toList());
    }

    @Override
    public Discount save(Discount discount) {
        DiscountEntity toSave = discountConverter.discountModelToEntity(discount);
        DiscountEntity saved = repository.save(toSave);
        return discountConverter.discountEntityToModel(saved);
    }

    @Override
    public void deleteByCode(String code) { repository.deleteByCode(code); }

    @Override
    public boolean existsByCode(String code) { return repository.existsByCode(code); }
}
