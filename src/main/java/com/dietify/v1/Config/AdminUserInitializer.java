package com.dietify.v1.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.dietify.v1.Entity.User;
import com.dietify.v1.Repository.UserRepo;


@Component
public class AdminUserInitializer implements ApplicationRunner {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!userRepository.existsByEmail("admin@dietify.com")) {
            User adminUser = new User();
            adminUser.setName("Admin");
            adminUser.setEmail("admin@dietify.com");
            adminUser.setPassword(encoder.encode("admin")); 
            adminUser.setRole("ROLE_ADMIN");
            adminUser.setVerificationStatus(true);
            userRepository.save(adminUser);
        }
    }
}
