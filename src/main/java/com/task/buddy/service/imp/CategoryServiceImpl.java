package com.task.buddy.service.imp;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.task.buddy.model.Category;
import com.task.buddy.model.Task;
import com.task.buddy.model.User;
import com.task.buddy.repository.CategoryRepository;
import com.task.buddy.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	private CategoryRepository categoryRepository;

	@Autowired
	public CategoryServiceImpl(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Override
	public void createCategory(Category category) {
		categoryRepository.save(category);
	}

	@Override
	public void updateCategory(Long id, Category updatedCategory) {
		Category category = categoryRepository.getOne(id);
		category.setName(updatedCategory.getName());
		category.setDescription(updatedCategory.getDescription());
		categoryRepository.save(category);
	}

	@Override
	public void deleteCategory(Long id) {
		categoryRepository.deleteById(id);
	}

	@Override
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	@Override
	public Category getCategoryById(Long id) {
		return categoryRepository.findById(id).orElse(null);
	}


}
