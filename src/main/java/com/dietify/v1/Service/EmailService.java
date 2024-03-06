package com.dietify.v1.Service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	public void sendEmail(String toEmail, String subject, String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(toEmail);
		message.setText(body);
		message.setSubject(subject);
		mailSender.send(message);
		System.out.println("Mail Send...");

	}

	public String generateResetToken() {
		return UUID.randomUUID().toString();
	}

	public void sendResetPasswordEmail(String email, String token) {
		String resetLink = "http://localhost:1111/reset?token=" + token;
		String subject = "Reset Your Password";
		String body = "To reset your password, click the following link: " + resetLink;
		sendEmail(email, subject, body);
	}

	public void sendVerificationMail(String email, String token) {
		String resetLink = "http://localhost:1111/verifyEmail?token=" + token;
		String subject = "Verify your email";
		String body = "To verify your email, click the following link: " + resetLink;
		sendEmail(email, subject, body);
	}
}
