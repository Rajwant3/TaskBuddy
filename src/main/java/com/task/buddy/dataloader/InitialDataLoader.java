package com.task.buddy.dataloader;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.task.buddy.model.Category;
import com.task.buddy.model.Role;
import com.task.buddy.model.Task;
import com.task.buddy.model.User;
import com.task.buddy.service.CategoryService;
import com.task.buddy.service.RoleService;
import com.task.buddy.service.TaskService;
import com.task.buddy.service.UserService;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	private CategoryService categoryService;
	private UserService userService;
	private TaskService taskService;
	private RoleService roleService;
	private final Logger logger = LoggerFactory.getLogger(InitialDataLoader.class);

	@Autowired
	public InitialDataLoader(UserService userService, TaskService taskService, RoleService roleService,
			CategoryService categoryService) {
		this.userService = userService;
		this.taskService = taskService;
		this.roleService = roleService;
		this.categoryService = categoryService;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		// ROLES
		// --------------------------------------------------------------------------------------------------------
		roleService.createRole(new Role("ADMIN"));
		roleService.createRole(new Role("USER"));
		roleService.findAll().stream().map(role -> "saved role: " + role.getRole()).forEach(logger::info);

		/**
		 * Initial Users
		 */

		// USERS
		// --------------------------------------------------------------------------------------------------------

		// 1 //Admin
		User admin = new User("niketa@lambton.com", "Niketa", "lambton", "images/niketa.png", true);
		userService.createUser(admin);
		userService.changeRoleToAdmin(admin);

		// 2
		userService.createUser(new User("saurav@lambton.com", "Saurav", "lambton", "images/saurav.png", true));

		// 3
		userService.createUser(new User("crisel@lambton.com", "Criselda", "lambton", "images/crisel.png", true));

		// 4
		userService.createUser(new User("reea@lambton.com", "Reea", "lambton", "images/reea.png", true));

		// 5
		userService.createUser(new User("sujita@lambton.com", "Sujita", "lambton", "images/sujita.png", true));

		// 7
		// Admin 2
		userService.changeRoleToAdmin(userService
				.createUser(new User("rajwant@lambton.com", "Rajwant", "lambton", "images/rajwant.png", true)));

		userService.findAll().stream().map(u -> "saved user: " + u.getName()).forEach(logger::info);

		/**
		 * Dummy Categories
		 */

		// 1
		categoryService.createCategory(
				new Category("Assignments", "All Tasks Related to Assignments will fall  under this category",
						userService.getUserByEmail("niketa@lambton.com").getName()));
		// 2
		categoryService
				.createCategory(new Category("Projects", "All Tasks Related to projects will fall  under this category",
						userService.getUserByEmail("niketa@lambton.com").getName()));

		// 3
		categoryService.createCategory(new Category("Class Notes/Lectures",
				"All Tasks Related to Notes/Lectures will fall  under this category",
				userService.getUserByEmail("niketa@lambton.com").getName()));

		// 4
		categoryService.createCategory(
				new Category("Class Quizzes/Exams", "All Tasks Related to Quizzes/Exams will fall  under this category",
						userService.getUserByEmail("niketa@lambton.com").getName()));

		// 5
		categoryService.createCategory(new Category("Dummy Category", "All Dummy Tasks will fall  under this category",
				userService.getUserByEmail("rajwant@lambton.com").getName()));

		/**
		 * Categories End
		 */

		/**
		 * Dummy Tasks List
		 */
		// TASKS
		// --------------------------------------------------------------------------------------------------------
		LocalDate today = LocalDate.now();

		// 1
		taskService.createTask(new Task("Create project report",
				"Create a detailed project report, covering all the specifications as instructed. Use relavant screenshots, explain codes and projects structure.",
				today.minusDays(40), true, userService.getUserByEmail("rajwant@lambton.com").getName(),
				userService.getUserByEmail("crisel@lambton.com"), 3));

		// 2
		taskService.createTask(new Task("Create Project UI template",
				"Design project UI, use all the relevants css, html, javascript, bootstap, jsp or thymleaf and create  a suitable graphical user interface for our task management project.",
				today.minusDays(30), true, userService.getUserByEmail("rajwant@lambton.com").getName(),
				userService.getUserByEmail("saurav@lambton.com"), 1));

		// 3
		taskService.createTask(new Task("User aunthentical module",
				"Design and develop user authentical module which should have following features: User Registration User login/logout Password recovery",
				today.minusDays(20), true, userService.getUserByEmail("rajwant@lambton.com").getName(),
				userService.getUserByEmail("crisel@lambton.com"), 2));

		// 4
		taskService.createTask(new Task("Task Management Module",
				"Design and develop task management module: \n Adding a new task, Editing existing tasks, Deleting tasks, Marking tasks as complete",
				today.minusDays(10), true, userService.getUserByEmail("rajwant@lambton.com").getName(),
				userService.getUserByEmail("saurav@lambton.com"), 2));

		// 5
		taskService.createTask(new Task("Category/Project Module",
				"Design and develop creating task categories or projects Assigning tasks to specific categories/projects",
				today.minusDays(5), false, userService.getUserByEmail("rajwant@lambton.com").getName(),
				userService.getUserByEmail("saurav@lambton.com"), 4));

		// 6
		taskService.createTask(new Task("Priority Module", "Setting task priorities\r\n" + "Sorting tasks by priority",
				today.minusDays(2), false, userService.getUserByEmail("rajwant@lambton.com").getName(),
				userService.getUserByEmail("rajwant@lambton.com"), 4));

		// 7
		taskService.createTask(new Task("Search and Filter Module:",
				"Searching for tasks\r\n"
						+ "Filtering tasks based on various criteria (e.g., date, priority, category)\r\n",
				today.minusDays(1), false, userService.getUserByEmail("rajwant@lambton.com").getName(),
				userService.getUserByEmail("sujita@lambton.com"), 5));

		// 8
		taskService.createTask(new Task("Collaboration Module:",
				"Sharing tasks with other users\r\n" + "Real-time updates for shared tasks", today, false,
				userService.getUserByEmail("rajwant@lambton.com").getName(),
				userService.getUserByEmail("rajwant@lambton.com"), 5));

		// 9
		taskService.createTask(new Task("Scheduling Module:",
				"Allowing users to schedule tasks for specific dates and times.\r\n"
						+ "Implementing recurring tasks or reminders.\r\n"
						+ "Sending notifications or reminders when a scheduled task is approaching.",
				today.plusDays(1), false, userService.getUserByEmail("rajwant@lambton.com").getName(),
				userService.getUserByEmail("reea@lambton.com"), 5));

		// 10
		taskService.createTask(new Task("Attachment and Notes Module:",
				"Enabling users to attach files or links to tasks.\r\n"
						+ "Adding notes or comments to tasks for additional details.\r\n"
						+ "Supporting multimedia attachments for more comprehensive task descriptions.\r\n",
				today.plusDays(2), false, userService.getUserByEmail("rajwant@lambton.com").getName(),
				userService.getUserByEmail("rajwant@lambton.com"), 2));

		// 11
		taskService.createTask(
				new Task("Grade Assignment 1", "Grade all students assignment 1 and provide detailed feedback",
						today.plusDays(3), false, userService.getUserByEmail("niketa@lambton.com").getName(),
						userService.getUserByEmail("niketa@lambton.com"), 2));

		// 12
		taskService.createTask(
				new Task("Grade Assignment 2", "Grade all students assignment 2 and provide detailed feedback",
						today.plusDays(4), false, userService.getUserByEmail("niketa@lambton.com").getName(),
						userService.getUserByEmail("niketa@lambton.com"), 1));

		// 13
		taskService.createTask(
				new Task("Grade Assignment 3", "Grade all students assignment 3 and provide detailed feedback",
						today.plusDays(5), false, userService.getUserByEmail("niketa@lambton.com").getName(),
						userService.getUserByEmail("niketa@lambton.com"), 2));

		// 14
		taskService.createTask(
				new Task("Grade Assignment 4", "Grade all students assignment 4 and provide detailed feedback",
						today.plusDays(6), false, userService.getUserByEmail("niketa@lambton.com").getName(),
						userService.getUserByEmail("niketa@lambton.com"), 3));

		// 15
		taskService.createTask(
				new Task("Grade Assignment 5", "Grade all students assignment 5 and provide detailed feedback",
						today.plusDays(7), false, userService.getUserByEmail("niketa@lambton.com").getName(),
						userService.getUserByEmail("niketa@lambton.com"), 4));
		// 16
		taskService
				.createTask(new Task("Grade Java Project", "Grade all students projects and provide detailed feedback",
						today.plusDays(10), false, userService.getUserByEmail("niketa@lambton.com").getName(),
						userService.getUserByEmail("niketa@lambton.com"), 1));
		// 17
		taskService.createTask(new Task("Project Code comments", "Add detailed comments, in all modules of code.",
				today.plusDays(3), false, userService.getUserByEmail("rajwant@lambton.com").getName(),
				userService.getUserByEmail("niketa@lambton.com"), 4));
		// 18
		taskService.createTask(new Task("Dummy Task 1", "Dummy Task Detailed description", today.plusDays(14), false,
				userService.getUserByEmail("rajwant@lambton.com").getName()));

		// 19
		taskService.createTask(new Task("Dummy Task 2", "Dummy Task Detailed description", today.plusDays(14), false,
				userService.getUserByEmail("rajwant@lambton.com").getName()));

		// 20
		taskService.createTask(new Task("Dummy Task 3", "Dummy Task Detailed description", today.plusDays(14), false,
				userService.getUserByEmail("rajwant@lambton.com").getName()));

		// 21
		taskService.createTask(new Task("Dummy Task 4", "Dummy Task Detailed description", today.plusDays(14), false,
				userService.getUserByEmail("rajwant@lambton.com").getName()));

		// 22
		taskService.createTask(new Task("Dummy Task 5", "Dummy Task Detailed description", today.plusDays(14), false,
				userService.getUserByEmail("rajwant@lambton.com").getName()));

		// 23
		taskService.createTask(new Task("Dummy Task 6", "Dummy Task Detailed description", today.plusDays(14), false,
				userService.getUserByEmail("rajwant@lambton.com").getName()));

		// 24
		taskService.createTask(new Task("Dummy Task 7", "Dummy Task Detailed description", today.plusDays(14), false,
				userService.getUserByEmail("rajwant@lambton.com").getName()));

		// 25
		taskService.createTask(new Task("Dummy Task 8", "Dummy Task Detailed description", today.plusDays(14), false,
				userService.getUserByEmail("rajwant@lambton.com").getName()));

		// 26
		taskService.createTask(new Task("Dummy Task 9", "Dummy Task Detailed description", today.plusDays(14), false,
				userService.getUserByEmail("rajwant@lambton.com").getName()));

		// 27
		taskService.createTask(new Task("Dummy Task 10", "Dummy Task Detailed description", today.plusDays(14), false,
				userService.getUserByEmail("rajwant@lambton.com").getName()));

		taskService.findAll().stream()
				.map(t -> "saved task: '" + t.getName() + "' for owner: " + getOwnerNameOrNoOwner(t))
				.forEach(logger::info);
	}

	private String getOwnerNameOrNoOwner(Task task) {
		return task.getOwner() == null ? "no owner" : task.getOwner().getName();
	}
}
