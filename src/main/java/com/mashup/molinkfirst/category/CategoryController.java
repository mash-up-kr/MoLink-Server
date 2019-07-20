package com.mashup.molinkfirst.category;

import com.mashup.molinkfirst.category.dto.ResCategories;
import com.mashup.molinkfirst.model.ApiResponseModel;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
  private CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  /* 카테고리 목록 뿌려주기 API */
  @GetMapping("")
  public ApiResponseModel<List<ResCategories>> getCategories(){
    ApiResponseModel<List<ResCategories>> response = new ApiResponseModel<List<ResCategories>>();
    response.setStatusCode(HttpStatus.OK.value());
    response.setMessage(HttpStatus.OK.toString());
    response.setData(categoryService.getAllCategories());

    return response;
  }
}
