package com.dietify.v1.Config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.dietify.v1.Entity.User;
import com.dietify.v1.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(username);

		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}

		if (user.getResetToken() == null && !user.isVerificationStatus()) {
			throw new UsernameNotFoundException("User account not verified yet");
		} else {
			return new CustomUser(user);
		}

	}
}