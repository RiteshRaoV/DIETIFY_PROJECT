package com.dietify.v1.Controllers;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dietify.v1.DTO.Day.DayResponse;
import com.dietify.v1.Entity.User;
import com.dietify.v1.Repository.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;

@RestController
public class MongoDBController {
    @Autowired
    private UserRepo userRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @PostMapping("/mongodb/store")
    public ResponseEntity<String> storeDataToMongoDB(HttpSession session) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = authentication.getName();
            User user = userRepository.findByEmail(userEmail);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }

            DayResponse dayResponse = (DayResponse) session.getAttribute("dayResponse");
            if (dayResponse == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No day response data found in session.");
            }

            ObjectMapper objectMapper = new ObjectMapper();
            String dayResponseJson = objectMapper.writeValueAsString(dayResponse);

            Document document = Document.parse(dayResponseJson);

            document.append("userID", user.getId());

            mongoTemplate.save(document, "mealPlans");

            return ResponseEntity.ok().body("Data stored successfully for user " + user.getId());
        } catch (JsonParseException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid JSON format.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error storing data.");
        }
    }

    @GetMapping("/mongodb/fetch")
    public ResponseEntity<String> fetchDataFromMongoDB(@RequestParam String userID) {
        String data = mongoTemplate.findAll(String.class, "mealPlans").toString();
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}