package com.task.buddy.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.task.buddy.model.Category;
import com.task.buddy.model.User;
import com.task.buddy.service.CategoryService;
import com.task.buddy.service.UserService;

@Controller
@RequestMapping("/category")
public class CategoryController {

	private CategoryService categoryService;
	private UserService userService;

	@Autowired
	public CategoryController(UserService userService, CategoryService categoryService) {
		this.categoryService = categoryService;
		this.userService = userService;
	}

	/**
	 * Tasks Categories list
	 * 
	 * @param model
	 * @param principal
	 * @return
	 */
	@GetMapping("/list")
	public String categories(Model model, Principal principal) {
		prepareCategoryListModel(model, principal);
		return "views/categories";
	}

	private void prepareCategoryListModel(Model model, Principal principal) {
		String email = principal.getName();
		User signedUser = userService.getUserByEmail(email);

		model.addAttribute("categories", categoryService.findAll());
		model.addAttribute("users", userService.findAll());
		model.addAttribute("signedUser", signedUser);
		model.addAttribute("isAdminSigned", signedUser.isAdmin());

	}

	/**
	 * Return view of creating a new category.
	 * 
	 * @param model
	 * @param principal
	 * @return
	 */
	@GetMapping("/create")
	public String showEmptyCategoryForm(Model model, Principal principal) {
		String email = principal.getName();
		User user = userService.getUserByEmail(email);

		Category category = new Category();
		category.setCreatorName(user.getName());
		model.addAttribute("category", category);
		return "views/category-new";
	}

	/**
	 * Save newly created category.
	 * 
	 * @param task
	 * @param bindingResult
	 * @return
	 */
	@PostMapping("/create")
	public String createCategory(@Valid Category category, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {

			return "views/category-new";
		}
		categoryService.createCategory(category);

		return "redirect:/category/list";
	}

	/**
	 * Return view to edit an existing category.
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/edit/{id}")
	public String showFilledCategoryForm(@PathVariable Long id, Model model) {
		model.addAttribute("category", categoryService.getCategoryById(id));
		return "views/category-edit";
	}

	/**
	 * Save edited category.
	 * 
	 * @param task
	 * @param bindingResult
	 * @param id
	 * @param model
	 * @return
	 */
	@PostMapping("/edit/{id}")
	public String updateCategory(@Valid Category category, BindingResult bindingResult, @PathVariable Long id,
			Model model) {
		if (bindingResult.hasErrors()) {
			return "views/category-edit";
		}
		categoryService.updateCategory(id, category);
		return "redirect:/category/list";
	}

	/**
	 * Delete a category
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/delete/{id}")
	public String deleteCategory(@PathVariable Long id) {
		categoryService.deleteCategory(id);
		return "redirect:/category/list";
	}
}
