package com.task.buddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Home page handler
 * 
 * @author Rajwant
 *
 */
@Controller
public class HomeController {
	@RequestMapping("/")
	public String showIndex() {
		return "index";
	}

	@GetMapping("/login")
	public String showLoginForm() {
		// login form is submitted using POST method <form th:action="@{/login}"
		// method="post">
		return "forms/login";
	}

	@RequestMapping("/about")
	public String showAboutPage() {
		return "views/about";
	}
}
