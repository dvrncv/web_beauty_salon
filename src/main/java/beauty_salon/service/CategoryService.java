package beauty_salon.service;

import beauty_salon.DTO.CategoryDTO;
import form.SearchInputModel;
import org.springframework.http.ResponseEntity;
import viewmodel.category.CategoryViewModel;
import viewmodel.service.CardServiceViewModel;

import java.util.List;

public interface CategoryService {
    ResponseEntity<CategoryDTO> createCategory(CategoryDTO categoryDTO);

    ResponseEntity<CategoryDTO> findById(Long id);

    ResponseEntity<CategoryDTO> updateCategory(Long id, CategoryDTO categoryDTO);

    List<CategoryDTO> getAllCategories();

    String getCategoryNameById(Long categoryId);

    List<CategoryDTO> getCategories();
}
