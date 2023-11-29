package com.task.buddy.service.imp;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.task.buddy.model.Role;
import com.task.buddy.model.User;
import com.task.buddy.repository.RoleRepository;
import com.task.buddy.repository.UserRepository;
import com.task.buddy.service.UserService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class UserServiceImpl implements UserService {
	private static final String ADMIN = "ADMIN";
	private static final String USER = "USER";

	private UserRepository userRepository;

	private RoleRepository roleRepository;

	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public User createUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		Role userRole = roleRepository.findByRole(USER);
		user.setRoles(new ArrayList<>(Collections.singletonList(userRole)));
		return userRepository.save(user);
	}

	@Override
	public User changeRoleToAdmin(User user) {
		Role adminRole = roleRepository.findByRole(ADMIN);
		user.setRoles(new ArrayList<>(Collections.singletonList(adminRole)));
		return userRepository.save(user);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public boolean isUserEmailPresent(String email) {
		return userRepository.findByEmail(email) != null;
	}

	@Override
	public User getUserById(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public void deleteUser(Long id) {
		User user = userRepository.getReferenceById(id);
		user.getTasksOwned().forEach(task -> task.setOwner(null));
		userRepository.delete(user);
	}

//	to send email to admin for enabling user to login
	@Override
	public void sendVerificationEmail(User user, String siteURL) {
		String subject = "Please Verify registration";
		String senderName = "TaskBuddy";
		String mailContent = "<p>Dear Admin</p>";
		mailContent += "<p> Please click the link below to verify the registration of user " + user.getName()
				+ " with Email " + user.getEmail() + ":</p>";
		String verifyURL = siteURL + "/verify?code="
				+ new String(Base64.getUrlEncoder().encode(user.getId().toString().getBytes()));
		mailContent += "<h3><a href=\"" + verifyURL + "\">VERIFY</a></h3>";
		mailContent += "<p> Thank you <br> Task Buddy </p>";

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
			helper.setFrom("rajwant@signtiysolutions.in", senderName);
			helper.setTo("rkdhillon3@gmail.com");
			helper.setSubject(subject);
			helper.setText(mailContent, true);
			mailSender.send(message);
		} catch (UnsupportedEncodingException | MessagingException e) {
			e.printStackTrace();
		}

	}

	@Transactional
	@Override
	public boolean verify(String encodedId) {
		String decodedId = new String(Base64.getUrlDecoder().decode(encodedId));
		Long longId = Long.parseLong(decodedId);

		userRepository.enable(longId);
		return true;
	}

	// To change the password
	@Transactional
	@Override
	public void updatePassword(User user, String newPassword) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword = encoder.encode(newPassword);
		user.setPassword(encodedPassword);
		userRepository.save(user);
	}
}
