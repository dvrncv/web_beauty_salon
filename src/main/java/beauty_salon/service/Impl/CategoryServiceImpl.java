package beauty_salon.service.Impl;

import beauty_salon.DTO.CategoryDTO;
import beauty_salon.entities.CategoryEntity;
import beauty_salon.exception.EntityNotFoundException;
import beauty_salon.repository.CategoryRepository;
import beauty_salon.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<CategoryDTO> createCategory(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = modelMapper.map(categoryDTO, CategoryEntity.class);
        CategoryEntity savedCategory = categoryRepository.save(categoryEntity);
        CategoryDTO savedDto = modelMapper.map(savedCategory, CategoryDTO.class);
        return ResponseEntity.ok().body(savedDto);
    }

    @Override
    public ResponseEntity<CategoryDTO> findById(Long id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category with id " + id + " not found"));
        CategoryDTO categoryDTO = modelMapper.map(categoryEntity, CategoryDTO.class);
        return ResponseEntity.ok().body(categoryDTO);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<CategoryEntity> category = categoryRepository.findAll();
        return category.stream()
                .map(categoryEntity -> modelMapper.map(categoryEntity, CategoryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public String getCategoryNameById(Long categoryId) {
        CategoryEntity category = categoryRepository.findById(categoryId).orElse(null);
        return category != null ? category.getName() : "Категория не найдена";
    }


    @Override
    public ResponseEntity<CategoryDTO> updateCategory(Long id, CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Категория с id " + id + " не найдена"));
        if (categoryDTO.getName() != null) {
            categoryEntity.setName(categoryDTO.getName());
        }
        if (categoryDTO.getDescription() != null) {
            categoryEntity.setDescription(categoryDTO.getDescription());
        }
        CategoryEntity updatedCategory = categoryRepository.save(categoryEntity);
        CategoryDTO updatedDto = modelMapper.map(updatedCategory, CategoryDTO.class);
        return ResponseEntity.ok().body(updatedDto);
    }


    @Override
    public List<CategoryDTO> getCategories() {
        List<CategoryEntity> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
    }
}
