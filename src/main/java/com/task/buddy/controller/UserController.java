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
import org.springframework.web.bind.annotation.RequestParam;

import com.task.buddy.model.User;
import com.task.buddy.service.UserService;
import com.task.buddy.utils.Utility;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UserController {

	private UserService userService;

//	private Utility utility;

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
	public String registerUser(@Valid User user, BindingResult bindingResult, Model model, HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			return "views/register";
		}

		if (userService.isUserEmailPresent(user.getEmail())) {
			model.addAttribute("exist", true);
			return "views/register";
		}

		userService.createUser(user);
		String siteURL = Utility.getSiteURL(request);
		userService.sendVerificationEmail(user, siteURL);
		model.addAttribute("H1_SUCCESS_MSG", "Registration success!");
		model.addAttribute("H3_SUCCESS_MSG", "You have signed up successfully!");
		model.addAttribute("P_SUCCESS_MSG", "Please wait, System Admin will verify your account soon.");
		return "views/success-msg";
	}

	@GetMapping("/verify")
	public String verifyAccount(@RequestParam("code") String encodedId, Model model) {
		boolean verified = userService.verify(encodedId);
		String pageTitle = verified ? "Verification Succedeed!" : "Verification Failed";
		model.addAttribute("pageTitle", pageTitle);
		model.addAttribute("H1_SUCCESS_MSG", "Verification success!");
		model.addAttribute("H3_SUCCESS_MSG", "User can login to their  account now.");
//		model.addAttribute("P_SUCCESS_MSG", "Please wait, System Admin will verify your account soon.");

		return (verified ? "views/verify_success" : "views/verify_fail");
	}
}
