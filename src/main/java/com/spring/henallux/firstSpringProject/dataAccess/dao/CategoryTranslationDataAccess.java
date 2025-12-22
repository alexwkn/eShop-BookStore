package com.spring.henallux.firstSpringProject.dataAccess.dao;

import com.spring.henallux.firstSpringProject.model.CategoryLabel;
import java.util.List;

public interface CategoryTranslationDataAccess {
    List<CategoryLabel> listCategoriesWithNames();
}
