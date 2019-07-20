package com.mashup.molinkfirst.category;

import com.mashup.molinkfirst.category.dto.ResCategories;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
  private CategoryRepository categoryRepository;

  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public List<ResCategories> getAllCategories(){
    List<Category> categories = categoryRepository.findAll();
    List<ResCategories> resCategories = new ArrayList<>();
    int categoryNum = categories.size();

    for (int i = 0; i < categoryNum; i++) {
      ResCategories resCategory = new ResCategories();
      resCategory.setId(categories.get(i).getId());
      resCategory.setName(categories.get(i).getName());
      resCategory.setImgUrl(categories.get(i).getImgUrl());
      resCategories.add(resCategory);
    }
    return resCategories;
  }
}
