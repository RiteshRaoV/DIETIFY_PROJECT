package com.dietify.v1.Controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dietify.v1.Entity.User;
import com.dietify.v1.Repository.UserRepo;
import com.dietify.v1.Service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepo userRepo;

	@ModelAttribute
	public void commonUser(Principal p, Model m) {
		if (p != null) {
			String email = p.getName();
			User user = userRepo.findByEmail(email);
			m.addAttribute("user", user);
		}

	}

	@GetMapping("/")
	public String landing() {
		return "home";
	}

	@GetMapping(value = { "/signin", "/signIn" })
	public String signIn() {
		return "signIn";
	}

	@GetMapping("/signUp")
	public String signUp() {
		return "signUp";
	}

	@PostMapping("/saveUser")
	public String saveUser(@ModelAttribute User user, HttpSession session, Model m) {
		User savedUser = userService.saveUser(user);

		if (savedUser != null) {
			session.setAttribute("msg", "Registered successfully");
			return "redirect:/signUp";
		} else {
			session.setAttribute("msg", "Something went wrong on the server");
		}
		return "redirect:/signUp";
	}

	@GetMapping("/verify")
	public String verify() {
		return "verify";
	}

	@PostMapping("/verify")
	public String register(@RequestParam String email, HttpSession session) {
		if (userService.existsByEmail(email)) {
			session.setAttribute("message", "Email address already exists");
			return "redirect:/verify";
		} else {
			userService.initiateMailValidation(email);
			session.setAttribute("message", "Verification link sent to your email id");
			return "redirect:/verify";
		}
	}

	@GetMapping("/verifyEmail")
	public String register(@RequestParam("token") String token, Model model, HttpSession session) {
		User user = userService.findUserByResetToken(token);
		if (user != null) {
			model.addAttribute("email", user.getEmail());
			model.addAttribute("token", token);
			return "signUp";
		} else {
			session.setAttribute("msg", "error while validating your email");
			return "signUp";
		}
	}

	@PostMapping("/forgot-password")
	public String forgotPassword(@RequestParam String email, HttpSession session) {
		if (userService.existsByEmail(email)) {
			userService.initiatePasswordReset(email);
			session.setAttribute("message", "Link sent to your email");
			return "redirect:/forgot-password";
		} else {
			session.setAttribute("message", "email does'nt exist");
			return "redirect:/forgot-password";
		}
	}

	@GetMapping("/forgot-password")
	public String forgotpassword() {
		return "forgot_password";
	}

	@GetMapping("/reset")
	public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
		User user = userService.findUserByResetToken(token);
		if (user != null) {
			model.addAttribute("email", user.getEmail());
			return "reset_password";
		} else {
			return "reset_password";
		}
	}

	@PostMapping("/reset")
	public String resetPassword(@RequestParam("email") String email, @RequestParam("token") String token,
			@RequestParam("password") String password) {
		userService.resetPassword(email, token, password);
		return "redirect:/signIn";
	}

	// @GetMapping("/userprofile")
	// public String viewProfile(Principal p, Model m) {
	// 	// Retrieve the user object from the session
	// 	if (p != null) {
	// 		String email = p.getName();
	// 		User user = userRepo.findByEmail(email);
	// 		m.addAttribute("user", user.getName());
	// 	}
	// 	return "profile";
	// }
}