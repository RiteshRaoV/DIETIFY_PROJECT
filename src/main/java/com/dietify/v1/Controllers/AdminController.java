package com.dietify.v1.Controllers;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dietify.v1.Entity.User;
import com.dietify.v1.Repository.UserRepo;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserRepo userRepo; // Use JPA repository for CRUD operations

	@GetMapping("/profile")
	public String profile(Model model) {
		// Check if user is authenticated
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			return "redirect:/index"; // Redirect to login if not authenticated
		}

		Principal principal = SecurityContextHolder.getContext().getAuthentication();
		String email = principal.getName();
		User user = userRepo.findByEmail(email); // Or userService.getCurrentUser()
		model.addAttribute("user", user);

		return "admin_profile"; // Render the admin profile page
	}

	@GetMapping("/users") // Revised endpoint for clarity
	public String getAllUsers(Model model) {
		List<User> users;
		users = userRepo.findAll(); // Use JPA repository for simplicity
		model.addAttribute("users", users);
		return "admin_users"; // Render the user data view
	}

	@PostMapping("/user/delete")
	public String deleteEmployee(@RequestParam("id") Integer userId) {
		userRepo.deleteById(userId);
		return "redirect:/admin/users";
	}
	@GetMapping("/user/assignRole")
    public String showAssignRoleForm(@RequestParam("userId") int userId, Model model) {
        // Here, you might fetch the user details using the userId and pass it to the view
        // For demonstration, I'm simply passing the userId to the view
        model.addAttribute("userId", userId);
        return "admin/assignRoleForm";
    }

	@PostMapping("/user/assignRole")
    public String assignRole(@RequestParam("userId") int userId, @RequestParam("role") String role) {
        Optional<User> userOptional = userRepo.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setRole(role);
            userRepo.save(user);
            return "redirect:/admin/users";
        } else {
            return "redirect:/admin/users"; // or any other appropriate action
        }
    }
}