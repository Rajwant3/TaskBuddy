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

import com.task.buddy.model.Task;
import com.task.buddy.model.User;
import com.task.buddy.service.CategoryService;
import com.task.buddy.service.TaskService;
import com.task.buddy.service.UserService;
import com.task.buddy.utils.Utility;

@Controller
public class TaskController {

	private TaskService taskService;
	private UserService userService;
	private CategoryService categoryService;

	@Autowired
	public TaskController(TaskService taskService, UserService userService, CategoryService categoryService) {
		this.taskService = taskService;
		this.userService = userService;
		this.categoryService = categoryService;
	}

	/**
	 * Tasks List
	 * 
	 * @param model
	 * @param principal
	 * @return
	 */
	@GetMapping("/tasks")
	public String listTasks(Model model, Principal principal) {
		prepareTasksListModel(model, principal);
		model.addAttribute("onlyInProgress", false);
		return "views/tasks";
	}

	@GetMapping("/tasks/in-progress")
	public String listTasksInProgress(Model model, Principal principal) {
		prepareTasksListModel(model, principal);
		model.addAttribute("onlyInProgress", true);
		return "views/tasks";
	}

	private void prepareTasksListModel(Model model, Principal principal) {
		String email = principal.getName();
		User signedUser = userService.getUserByEmail(email);

		model.addAttribute("tasks", taskService.findAll());
		model.addAttribute("users", userService.findAll());
		model.addAttribute("signedUser", signedUser);
		model.addAttribute("isAdminSigned", signedUser.isAdmin());
		model.addAttribute("categories", categoryService.findAll());

	}

	/**
	 * Return view of creating a new task.
	 * 
	 * @param model
	 * @param principal
	 * @return
	 */
	@GetMapping("/task/create")
	public String showEmptyTaskForm(Model model, Principal principal) {
		String email = principal.getName();
		User user = userService.getUserByEmail(email);

		Task task = new Task();
		task.setCreatorName(user.getName());
		if (user.getRoleNames().contains("USER")) {
			task.setOwner(user);
		}
		model.addAttribute("task", task);
		model.addAttribute("priorityMap", Utility.priorityMap);
		model.addAttribute("categories", categoryService.findAll());

		return "views/task-new";
	}

	/**
	 * Save newly created tasks.
	 * 
	 * @param task
	 * @param bindingResult
	 * @return
	 */
	@PostMapping("/task/create")
	public String createTask(@Valid Task task, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "views/task-new";
		}
		taskService.createTask(task);

		return "redirect:/tasks";
	}

	/**
	 * Return view to edit an existing task.
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/task/edit/{id}")
	public String showFilledTaskForm(@PathVariable Long id, Model model) {
		model.addAttribute("task", taskService.getTaskById(id));
		model.addAttribute("priorityMap", Utility.priorityMap);
		model.addAttribute("categories", categoryService.findAll());
		return "views/task-edit";
	}

	/**
	 * Save edited task.
	 * 
	 * @param task
	 * @param bindingResult
	 * @param id
	 * @param model
	 * @return
	 */
	@PostMapping("/task/edit/{id}")
	public String updateTask(@Valid Task task, BindingResult bindingResult, @PathVariable Long id, Model model) {
		if (bindingResult.hasErrors()) {
			return "views/task-edit";
		}
		taskService.updateTask(id, task);
		return "redirect:/tasks";
	}

	/**
	 * Delete a task
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/task/delete/{id}")
	public String deleteTask(@PathVariable Long id) {
		taskService.deleteTask(id);
		return "redirect:/tasks";
	}

	/**
	 * Completed task
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/task/mark-done/{id}")
	public String setTaskCompleted(@PathVariable Long id) {
		taskService.setTaskCompleted(id);
		return "redirect:/tasks";
	}

	@GetMapping("/task/unmark-done/{id}")
	public String setTaskNotCompleted(@PathVariable Long id) {
		taskService.setTaskNotCompleted(id);
		return "redirect:/tasks";
	}

}
