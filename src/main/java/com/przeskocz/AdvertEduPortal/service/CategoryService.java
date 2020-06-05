package com.przeskocz.AdvertEduPortal.service;

import com.przeskocz.AdvertEduPortal.DAO.CategoryDAO;
import com.przeskocz.AdvertEduPortal.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryDAO categoryDAO;

    @Autowired
    public CategoryService(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    public Category findOrCreate(Category category) {
        if (category == null)
            throw new NullPointerException("The category param cannot be null!");
        Category result = categoryDAO.findById(category.getId()).orElse(null);
        if (result == null)
            result = categoryDAO.findByNameContainingIgnoreCase(category.getName()).orElse(null);
        if (result == null)
            result = categoryDAO.save(category);

        return  result;
    }
}
