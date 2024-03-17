package com.dietify.v1.Service;

import java.time.LocalDateTime;
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
		String password = passwordEncoder.encode(user.getPassword());
		user.setPassword(password);
		user.setRole("ROLE_USER");
		User newuser = userRepo.save(user);
		return newuser;
	}

	@Override
	public void initiatePasswordReset(String email) {
		User user = userRepo.findByEmail(email);
		if (user != null) {
			String resetToken = emailService.generateResetToken();
			user.setResetToken(resetToken);
			user.setPasswordResetTokenDateTime(LocalDateTime.now());
			userRepo.save(user);
			emailService.sendResetPasswordEmail(user.getEmail(), resetToken);
		}
	}

	@Override
	public void initiateMailValidation(String email) {
		String verificationToken = emailService.generateResetToken();
		User user=userRepo.findByEmail(email);
		user.setResetToken(verificationToken);
		user.setVerificationTokenDateTime(LocalDateTime.now());
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