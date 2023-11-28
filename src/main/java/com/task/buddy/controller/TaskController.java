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
import com.task.buddy.service.TaskService;
import com.task.buddy.service.UserService;

@Controller
public class TaskController {

	private TaskService taskService;
	private UserService userService;

	@Autowired
	public TaskController(TaskService taskService, UserService userService) {
		this.taskService = taskService;
		this.userService = userService;
	}

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

	}

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
		return "views/task-new";
	}

	@PostMapping("/task/create")
	public String createTask(@Valid Task task, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "views/task-new";
		}
		taskService.createTask(task);

		return "redirect:/tasks";
	}

	@GetMapping("/task/edit/{id}")
	public String showFilledTaskForm(@PathVariable Long id, Model model) {
		model.addAttribute("task", taskService.getTaskById(id));
		return "views/task-edit";
	}

	@PostMapping("/task/edit/{id}")
	public String updateTask(@Valid Task task, BindingResult bindingResult, @PathVariable Long id, Model model) {
		if (bindingResult.hasErrors()) {
			return "views/task-edit";
		}
		taskService.updateTask(id, task);
		return "redirect:/tasks";
	}

	@GetMapping("/task/delete/{id}")
	public String deleteTask(@PathVariable Long id) {
		taskService.deleteTask(id);
		return "redirect:/tasks";
	}

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
