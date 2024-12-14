package beauty_salon.controllers;

import beauty_salon.DTO.CategoryDTO;
import beauty_salon.service.CategoryService;
import controllers.CategoryController;
import form.CreateCategoryInputModel;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import viewmodel.category.CategoryViewModel;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/category")
public class CategoryControllerImpl implements CategoryController {
    private final CategoryService categoryService;

    public CategoryControllerImpl(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @GetMapping("/list")
    public String listCategories(Model model) {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        List<CategoryViewModel> categoryViewModels = categories.stream()
                .map(categoryDTO -> {
                    CategoryViewModel categoryViewModel = new CategoryViewModel();
                    categoryViewModel.setId(categoryDTO.getId());
                    categoryViewModel.setName(categoryDTO.getName());
                    categoryViewModel.setDescription(categoryDTO.getDescription());
                    return categoryViewModel;
                })
                .collect(Collectors.toList());
        model.addAttribute("categories", categoryViewModels);
        return "CategoryList.html";
    }

    @GetMapping("/create")
    public String createCategory(Model model){
        return "CategoryList.html";
    }

    @Override
    @PostMapping("/create")
    public String createCategory(
            @Valid @ModelAttribute("createCategory") CreateCategoryInputModel createCategory,
            Model model
    ) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName(createCategory.getName());
        categoryDTO.setDescription(createCategory.getDescription());
        categoryService.createCategory(categoryDTO);
        model.addAttribute("message", "Category created successfully!");
        return "redirect:/category/list";
    }
    @GetMapping("/update/{id}")
    public String updateCategory(
            @PathVariable("id") Long id, Model model
    )
    {
        CategoryDTO categoryDTO = categoryService.findById(id).getBody();
        model.addAttribute("updateCategory", categoryDTO);
        return "CategoryList.html";
    }

    @Override
    @PostMapping("/update/{id}")
    public String updateCategory(
            @PathVariable("id") Long id,
            @Valid @ModelAttribute("updateCategory") CreateCategoryInputModel createCategoryInputModel,
            Model model
    ) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName(createCategoryInputModel.getName());
        categoryDTO.setDescription(createCategoryInputModel.getDescription());
        categoryService.updateCategory(id, categoryDTO);
        model.addAttribute("message", "Category updated successfully!");
        return "redirect:/category/list";
    }
}
