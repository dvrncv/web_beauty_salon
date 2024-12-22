package beauty_salon.controllers;

import beauty_salon.DTO.CategoryDTO;
import beauty_salon.service.CategoryService;
import controllers.CategoryController;
import form.CreateCategoryInputModel;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import viewmodel.category.CategoryViewModel;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CategoryControllerImpl implements CategoryController {
    private final CategoryService categoryService;
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    public CategoryControllerImpl(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public String listCategories(Principal principal, Model model) {
        LOG.info("GET:/category/list Show all categories request from " + principal.getName());

        List<CategoryDTO> categories = categoryService.getAllCategories();
        List<CategoryViewModel> categoryViewModels = categories.stream()
                .map(category -> new CategoryViewModel(
                        category.getId(),
                        category.getName(),
                        category.getDescription()))
                .collect(Collectors.toList());
        model.addAttribute("categories", categoryViewModels);
        return "CategoryList.html";
    }

    @Override
    public String createCategory(Principal principal, Model model){
        LOG.info("GET:/category/create Create category request from " + principal.getName());

        model.addAttribute("createCategory", new CreateCategoryInputModel());
        return "CategoryList.html";
    }

    @Override
    public String createCategory(@Valid @ModelAttribute("createCategory") CreateCategoryInputModel createCategory,
                                 Principal principal, Model model) {
        LOG.info("POST:/category/create Create category request from " + principal.getName());

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName(createCategory.getName());
        categoryDTO.setDescription(createCategory.getDescription());
        categoryService.createCategory(categoryDTO);
        return "redirect:/category/list";
    }

    @Override
    public String updateCategory(@PathVariable("id") Long id,
                                 Principal principal, Model model) {
        LOG.info("GET:/category/update Update category request from " + principal.getName());

        CategoryDTO categoryDTO = categoryService.findById(id).getBody();
        model.addAttribute("updateCategory", categoryDTO);
        return "CategoryList.html";
    }

    @Override
    public String updateCategory(@PathVariable("id") Long id,
                                 @Valid @ModelAttribute("updateCategory") CreateCategoryInputModel createCategoryInputModel, Principal principal,
                                 Model model) {
        LOG.info("POST:/category/update Update category request from " + principal.getName());

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName(createCategoryInputModel.getName());
        categoryDTO.setDescription(createCategoryInputModel.getDescription());
        categoryService.updateCategory(id, categoryDTO);
        return "redirect:/category/list";
    }
}
