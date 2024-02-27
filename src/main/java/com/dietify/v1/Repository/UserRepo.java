package com.dietify.v1.Repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dietify.v1.Entity.User;


@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

	public User findByEmail(String email);

	User findByEmailAndResetToken(String email, String resetToken);
	
	boolean existsByEmail(String email);

	public User findByResetToken(String token);

}
