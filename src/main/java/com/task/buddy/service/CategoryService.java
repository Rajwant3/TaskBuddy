package com.task.buddy.service;

import java.util.List;

import com.task.buddy.model.Category;

public interface CategoryService {

	void createCategory(Category category);

	void updateCategory(Long id, Category category);

	void deleteCategory(Long id);

	List<Category> findAll();

	Category getCategoryById(Long id);

//    List<Task> findByOwnerOrderByDateDesc(User user);
//
//    void setTaskCompleted(Long id);
//
//    void setTaskNotCompleted(Long id);
//
//    List<Task> findFreeTasks();
//
//    Task getTaskById(Long taskId);

//    void assignTaskToUser(Category category, User user);

//    void unassignTask(Task task);
}
