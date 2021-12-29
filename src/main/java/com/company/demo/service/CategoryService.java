package com.company.demo.service;

import com.company.demo.entity.Category;
import com.company.demo.model.dto.CategoryInfo;
import com.company.demo.model.request.CreateCategoryReq;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {

  List<Category> getListCategory();

  List<CategoryInfo> getListCategoryAndProductCount();

  Category createCategory(CreateCategoryReq req);

  void updateCategory(int id, CreateCategoryReq req);

  void deleteCategory(int id);
}
