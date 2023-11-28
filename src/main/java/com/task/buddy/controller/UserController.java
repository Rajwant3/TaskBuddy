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

import com.task.buddy.model.User;
import com.task.buddy.service.UserService;

@Controller
public class UserController {

	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/users")
	public String listUsers(Model model, Principal principal) {

		String email = principal.getName();
		User signedUser = userService.getUserByEmail(email);

		model.addAttribute("users", userService.findAll());
		model.addAttribute("isAdminSigned", signedUser.isAdmin());
		return "views/users";
	}

	@GetMapping("user/delete/{id}")
	public String deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return "redirect:/users";
	}

	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("user", new User());
		return "views/register";
	}

	@PostMapping("/register")
	public String registerUser(@Valid User user, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "views/register";
		}

		if (userService.isUserEmailPresent(user.getEmail())) {
			model.addAttribute("exist", true);
			return "register";
		}

		userService.createUser(user);
		return "views/success";
	}
}
