package com.task.buddy.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.task.buddy.model.User;
import com.task.buddy.service.UserService;

/**
 * Home page handler
 * 
 * @author Rajwant
 *
 */
@Controller
public class HomeController {

	@Autowired
	private UserService userService;

	@RequestMapping("/")
	public String showIndex(Model model, Principal principal) {

		if (principal != null) {
			String email = principal.getName();
			User signedUser = userService.getUserByEmail(email);
			model.addAttribute("userName", signedUser.getName());
		}
		return "views/home";
//		}else {
//			return "views/login";
//		}

	}

	@GetMapping("/login")
	public String showLoginForm() {
		// login form is submitted using POST method <form th:action="@{/login}"
		// method="post">
		return "views/login";
	}

	@RequestMapping("/about")
	public String showAboutPage() {
		return "views/index";
	}
}
