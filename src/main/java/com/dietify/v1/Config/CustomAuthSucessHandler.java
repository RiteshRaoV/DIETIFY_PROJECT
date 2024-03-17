package com.dietify.v1.Config;

import java.io.IOException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.dietify.v1.Entity.User;
import com.dietify.v1.Repository.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthSucessHandler implements AuthenticationSuccessHandler {

	@Autowired
    private UserRepo userRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        String username = authentication.getName();
        User user = userRepo.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        if (!user.isVerificationStatus()) {
			request.getSession().setAttribute("verificationMessage", "Your account is not verified. Please verify your account.");
            response.sendRedirect("/signIn"); // Redirect to a page for verifying the account
            return;
        }

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("ROLE_ADMIN")) {
            response.sendRedirect("/admin/users");
        } else if (roles.contains("ROLE_BLOGGER")) {
            response.sendRedirect("/blogs/myBlogs");
        } else {
            response.sendRedirect("/home");
        }
    }

}