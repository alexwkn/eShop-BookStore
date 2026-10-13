package com.eshop.bookstore.dataAccess.dao;

import com.eshop.bookstore.model.CategoryLabel;
import java.util.List;

public interface CategoryTranslationDataAccess {
    List<CategoryLabel> listCategoriesWithNames();
}
