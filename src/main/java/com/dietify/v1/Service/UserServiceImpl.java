package com.dietify.v1.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.dietify.v1.Entity.User;
import com.dietify.v1.Repository.UserRepo;

import jakarta.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	@Autowired
	private EmailService emailService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public boolean existsByEmail(String email) {
		return userRepo.existsByEmail(email);
	}

	@Override
	public User findUserByResetToken(String token) {
		return userRepo.findByResetToken(token);
	}

	@Override
	public void removeSessionMessage() {

		HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest()
				.getSession();

		session.removeAttribute("msg");
	}

	@Override
	public User saveUser(User user) {
		String email = user.getEmail();
		String token = user.getResetToken();
		User existingUser = userRepo.findByEmailAndResetToken(email, token);
		if (existingUser != null) {
			String encodedPassword = passwordEncoder.encode(user.getPassword());
			existingUser.setName(user.getName());
			existingUser.setPassword(encodedPassword);
			existingUser.setResetToken(null);
			userRepo.save(existingUser);
			return existingUser;
		}
		return null;
	}

	@Override
	public void saveUserWithEmailAndToken(String email, String verificationToken) {
		User user = new User();
		user.setEmail(email);
		user.setResetToken(verificationToken);
		user.setRole("ROLE_USER");
		userRepo.save(user);
	}

	@Override
	public void initiatePasswordReset(String email) {
		User user = userRepo.findByEmail(email);
		if (user != null) {
			String resetToken = emailService.generateResetToken();
			user.setResetToken(resetToken);
			userRepo.save(user);
			emailService.sendResetPasswordEmail(user.getEmail(), resetToken);
		}
	}

	@Override
	public void initiateMailValidation(String email) {
		String verificationToken = emailService.generateResetToken();
		User user = new User();
		user.setEmail(email);
		user.setResetToken(verificationToken);
		user.setRole("ROLE_USER");
		userRepo.save(user);
		emailService.sendVerificationMail(email, verificationToken);
	}

	@Override
	public void resetPassword(String email, String token, String newPassword) {
		User user = userRepo.findByEmailAndResetToken(email, token);
		if (user != null) {
			String password = passwordEncoder.encode(newPassword);
			user.setPassword(password);
			user.setResetToken(null);
			userRepo.save(user);
		}
	}

	@Override
	public List<User> findAll() {
		return userRepo.findAll();
	}

}