package com.dietify.v1.Service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	private void sendEmail(String to, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			helper.setFrom("ritheshraov016@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true); 
            mailSender.send(message);
        } catch (MessagingException e) {
			System.out.println("mail can't be sent..");
		}
	}

	public String generateResetToken() {
		return UUID.randomUUID().toString();
	}

	public void sendResetPasswordEmail(String email, String token) {
		String resetLink = "http://localhost:1111/reset?token=" + token;
		String subject = "Reset Your Password";

		// Relative path to the logo in your project's static resources
		String logoPath = "https://github.com/RiteshRaoV/project_final/blob/main/src/main/resources/static/Images/logo.png?raw=true";

		// Email body with logo on top
		String body = "<!DOCTYPE html>"
				+ "<html lang=\"en\">"
				+ "<head>"
				+ "<meta charset=\"UTF-8\">"
				+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
				+ "<title>Email Template</title>"
				+ "<style>"
				+ "body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f5f5f5; }"
				+ ".container { max-width: 600px; margin: 0 auto; padding: 20px; background-color: #fff; border-radius: 10px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }"
				+ ".logo { display: block; margin: 0 auto; max-width: 200px; height: auto; }"
				+ ".content { padding: 20px; text-align: center; }"
				+ "</style>"
				+ "</head>"
				+ "<body>"
				+ "<div class=\"container\">"
				+ "<img src=\"" + logoPath + "\" alt=\"Dietify Logo\" class=\"logo\">"
				+ "<div class=\"content\">"
				+ "<h2>Reset Your Password</h2>"
				+ "<p>To reset your password, click the following link:</p>"
				+ "<p><a href=\"" + resetLink + "\">Reset Password</a></p>"
				+ "</div>"
				+ "</div>"
				+ "</body>"
				+ "</html>";

		sendEmail(email, subject, body);
	}

	public void sendVerificationMail(String email, String token) {
		String verifyLink = "http://localhost:1111/verifyEmail?token=" + token;
		String subject = "Verify your email";

		// Logo image URL
		String logoUrl = "https://github.com/RiteshRaoV/project_final/blob/main/src/main/resources/static/Images/logo.png?raw=true";

		// Email body with logo on top
		String body = "<!DOCTYPE html>"
				+ "<html lang=\"en\">"
				+ "<head>"
				+ "<meta charset=\"UTF-8\">"
				+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
				+ "<title>Email Template</title>"
				+ "<style>"
				+ "body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f5f5f5; }"
				+ ".container { max-width: 600px; margin: 0 auto; padding: 20px; background-color: #fff; border-radius: 10px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }"
				+ ".logo { display: block; margin: 0 auto; max-width: 200px; height: auto; }"
				+ ".content { padding: 20px; text-align: center; }"
				+ "</style>"
				+ "</head>"
				+ "<body>"
				+ "<div class=\"container\">"
				+ "<img src=\"" + logoUrl + "\" alt=\"Dietify Logo\" class=\"logo\">"
				+ "<div class=\"content\">"
				+ "<h2>Welcome to Dietify!</h2>"
				+ "<p>Thank you for joining us. With Dietify, you can explore a variety of delicious recipes tailored to your preferences.</p>"
				+ "<p>Get started today and embark on a journey of culinary delights!</p>"
				+ "<p>To verify your email, click the following link:</p>"
				+ "<p><a href=\"" + verifyLink + "\">Verify Email</a></p>"
				+ "</div>"
				+ "</div>"
				+ "</body>"
				+ "</html>";

		sendEmail(email, subject, body);
	}

}
