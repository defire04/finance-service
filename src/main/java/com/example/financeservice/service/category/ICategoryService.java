package com.example.financeservice.service.category;

import com.example.financeservice.model.category.Category;
import com.example.financeservice.service.base.IBaseEntityService;
import org.springframework.data.domain.Page;

public interface ICategoryService extends IBaseEntityService<Category> {
    Page<Category> getAll(String username, int size, int page);

}
