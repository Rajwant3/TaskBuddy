package com.task.buddy.controller;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.task.buddy.model.User;
import com.task.buddy.repository.UserRepository;
import com.task.buddy.service.UserService;
import com.task.buddy.utils.Utility;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
//import net.bytebuddy.utility.RandomString;

@Controller
public class ForgotPasswordController {

	@Autowired
	private UserRepository userRepo;

	private Utility utility;

	@Autowired
	private UserService userService;

	@Autowired
	private JavaMailSender mailSender;

	@GetMapping("/forgot_password")
	public String viewForgotPasswordForm(Model model) {
		model.addAttribute("pageTitle", "Forgot Password");
		return "views/forgot_password_form";

	}

	@PostMapping("/forgot_password")
	public String processForgetPasswordRequest(HttpServletRequest request, Model model) {
		String email = request.getParameter("email");
		User user = userRepo.findByEmail(email);

		if (user == null) {
			model.addAttribute("H3_SUCCESS_MSG", "User Email not found");
			model.addAttribute("P_SUCCESS_MSG", "You need to create your account.");

			return "views/success-msg";
		}
		try {
//			resetPasswordlink to be sent to user through email
			String resetPasswordLink = utility.getSiteURL(request) + "/reset_password?token="
					+ new String(Base64.getUrlEncoder().encode(user.getId().toString().getBytes()));
			sendResetPasswordEmail(email, resetPasswordLink);
			model.addAttribute("message", "We have sent a reset password link on your email. Please check.");

			model.addAttribute("H3_SUCCESS_MSG", "We have mailed you a reset password link. Please check.");

		} catch (Exception e) {
			model.addAttribute("H3_SUCCESS_MSG", e.getMessage());
		}
		return "views/success-msg";

	}

	private void sendResetPasswordEmail(String email, String resetPasswordLink)
			throws UnsupportedEncodingException, MessagingException {
		String subject = " Link to Reset Password";
		String senderName = "Task Buddy";
		String mailContent = "<p>Hello</p>" + "<p> You have requested to reset your password on Task Buddy.</p>"
				+ "<p> Please click the link below to reset your password:</p>" + "<p><b><a href=\"" + resetPasswordLink
				+ "\">RESET PASSWORD</a><b></p>" + "<p>Ignore this if you have not requested to change the mail</p>";
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom("rajwant@signitysolutions.in", senderName);
		helper.setTo(email);
		helper.setSubject(subject);
		helper.setText(mailContent, true);
		mailSender.send(message);

	}

	/**
	 * New Password Form
	 * 
	 * @param token
	 * @param model
	 * @return
	 */
	@GetMapping("/reset_password")
	public String showResetPasswordForm(@RequestParam(value = "token") String token, Model model) {
		String decodedId = new String(Base64.getUrlDecoder().decode(token));
		Long longId = Long.parseLong(decodedId);
		User user = userService.getUserById(longId);
		if (user == null) {
			model.addAttribute("title", "Reset Your Password");
			model.addAttribute("H3_SUCCESS_MSG", "Invalid Token");
			return "views/success-msg";
		}
		model.addAttribute("pageTitle", "Reset Your Password");
		model.addAttribute("token", token);
		return "views/reset_Password_Form";

	}

	@PostMapping("/reset_password")
	public String processResetPasswordRequest(HttpServletRequest request, Model model) {
		String token = request.getParameter("token");
		String password = request.getParameter("password");
		String decodedId = new String(Base64.getUrlDecoder().decode(token));
		Long longId = Long.parseLong(decodedId);

		User user = userService.getUserById(longId);
		if (user == null) {
			model.addAttribute("title", "Reset Your Password");
			model.addAttribute("H3_SUCCESS_MSG", "Invalid Token");
			return "views/success-msg";
		} else {
			userService.updatePassword(user, password);
			model.addAttribute("title", "Password Reset");
			model.addAttribute("H1_SUCCESS_MSG", "You have successfully changed your password");
			model.addAttribute("H3_SUCCESS_MSG", "You may login with your new password now.");

			return "views/success-msg";
		}
	}

}
