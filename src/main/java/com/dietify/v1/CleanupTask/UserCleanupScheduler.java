package com.dietify.v1.CleanupTask;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dietify.v1.Entity.User;
import com.dietify.v1.Repository.UserRepo;

@Component
public class UserCleanupScheduler {

    @Autowired
    private UserRepo userRepo;


    @Scheduled(cron = "0 0 0 * * *") 
    private void cleanupVerificationTokens() {
        List<User> users = userRepo.findByVerificationStatus(false);
        for (User user : users) {
            userRepo.delete(user);
        }
    }
    
    @Scheduled(fixedRate = 300000)
    public void resetPasswordTokens() {
        LocalDateTime fiveMinutesAgo = LocalDateTime.now().minusMinutes(5);
        List<User> users = userRepo.findByPasswordResetTokenDateTimeBefore(fiveMinutesAgo);
        for (User user : users) {
            user.setResetToken(null);
            user.setPasswordResetTokenDateTime(null);
            userRepo.save(user);
        }
    }
}
