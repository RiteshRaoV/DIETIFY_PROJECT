package com.dietify.v1.CleanupTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dietify.v1.Entity.User;
import com.dietify.v1.Repository.UserRepo;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class UserCleanupScheduler {

    @Autowired
    private UserRepo userRepository;

    // Scheduled method to clean up tokens every 24 hours
    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanUpExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();
        List<User> usersWithExpiredTokens = userRepository.findByResetTokenExpiryDateTimeBefore(now);
        for (User user : usersWithExpiredTokens) {
            user.setResetToken(null);
            user.setResetTokenExpiryDateTime(null);
            userRepository.save(user);
        }
    }

    // Scheduled method to clean up users with only email, token, and role after 5 minutes of token creation
    @Scheduled(fixedRate = 300000) // 5 minutes in milliseconds
    public void cleanUpUsersWithIncompleteData() {
        LocalDateTime fiveMinutesAgo = LocalDateTime.now().minusMinutes(5);
        List<User> incompleteUsers = userRepository.findByPasswordIsNullAndResetTokenIsNotNullAndRoleIsNotNull();
        for (User user : incompleteUsers) {
            LocalDateTime tokenCreationTime = user.getResetTokenCreationDateTime();
            if (tokenCreationTime != null && tokenCreationTime.isBefore(fiveMinutesAgo)) {
                userRepository.delete(user);
            }
        }
    }
}
