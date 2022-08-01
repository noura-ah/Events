package com.example.events.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.events.models.User;
import com.example.events.requests.UserLoginRequest;
import com.example.events.services.UserService;


@Controller
public class AuthController {
	private final UserService userService;
	public AuthController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/")
	public String ShowRegisterLogin(Model model) {
		if(!model.containsAttribute("newUser")) {
			model.addAttribute("newUser",new User());
		}
		if(!model.containsAttribute("loginUser")) {
			model.addAttribute("loginUser", new UserLoginRequest());
		}
		return "index.jsp";
		
	}
	
	@PostMapping("/register")
	public String createUser(
			@Valid @ModelAttribute("newUser") User newUser,
			BindingResult result,
			RedirectAttributes redirectAttributes,
			HttpSession session) {
		User user = userService.createUser(newUser, result);
		if (user== null) {
			redirectAttributes.addFlashAttribute("newUser", newUser);
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.newUser", result);
			return "redirect:/";
		}
		else {
			session.setAttribute("user_id", user.getId());
			return "redirect:/events";
		}
		
	}
	@PostMapping("/login")
	public String userLogin(
			@Valid @ModelAttribute("loginUser") UserLoginRequest user,
			BindingResult result,
			RedirectAttributes redirectAttributes,
			HttpSession session) {
		if(result.hasErrors()) {
			redirectAttributes.addFlashAttribute("loginUser", user);
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.loginUser", result);
			return "redirect:/";
		}

		User userLogged = userService.loginUser(user, result);
		if (userLogged== null) {
			redirectAttributes.addFlashAttribute("loginUser", user);
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.loginUser", result);
			return "redirect:/";
		}
		else {
			session.setAttribute("user_id", userLogged.getId());
			return "redirect:/events";
		}
		
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user_id");
		return "redirect:/";
	}
}
