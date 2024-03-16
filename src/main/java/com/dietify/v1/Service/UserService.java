package com.dietify.v1.Service;


import java.util.List;

import com.dietify.v1.Entity.User;

public interface UserService {

	public User saveUser(User user);

	public void removeSessionMessage();

	public boolean existsByEmail(String email);

    public void resetPassword(String email, String token, String newPassword);

	public void initiatePasswordReset(String email);

	public void initiateMailValidation(String email);

	public User findUserByResetToken(String token);

	public List<User> findAll();
}
