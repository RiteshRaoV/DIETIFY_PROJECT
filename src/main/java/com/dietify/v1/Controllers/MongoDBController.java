package com.dietify.v1.Controllers;

import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dietify.v1.DTO.DatabaseResponse;
import com.dietify.v1.DTO.Day.DayResponse;
import com.dietify.v1.DTO.Week.WeekResponse;
import com.dietify.v1.Entity.User;
import com.dietify.v1.Repository.FavouriteRepo;
import com.dietify.v1.Repository.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.data.mongodb.core.query.Query;
import jakarta.servlet.http.HttpSession;

@Controller
public class MongoDBController {
    @Autowired
    private UserRepo userRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private FavouriteRepo favRepository;

    @PostMapping("/mongodb/store")
    public ResponseEntity<String> storeDataToMongoDB(@RequestParam String userText, HttpSession session) {
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

            // document.append("userID", user.getId());
            // document.append("title", userText);
            // document.append("type", "day");

            mongoTemplate.save(document, "mealPlans");
            Query query = new Query().limit(1).with(Sort.by(Sort.Order.desc("timestampField")));
            query.fields().include("_id");
            mongoTemplate.find(query,ObjectId.class);
            
            return ResponseEntity.ok().body("Data stored successfully for user " + user.getId());
        } catch (JsonParseException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid JSON format.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error storing data.");
        }
    }

    @PostMapping("/mongodb/store-week")
    public ResponseEntity<String> storeWeekDataToMongoDB(@RequestParam String userText, HttpSession session) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = authentication.getName();
            User user = userRepository.findByEmail(userEmail);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }

            WeekResponse weekResponse = (WeekResponse) session.getAttribute("weekResponse");
            if (weekResponse == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No week response data found in session.");
            }

            ObjectMapper objectMapper = new ObjectMapper();
            String weekResponseJson = objectMapper.writeValueAsString(weekResponse);

            Document document = Document.parse(weekResponseJson);

            // document.append("userID", user.getId());
            // document.append("title", userText);
            // document.append("type", "week");

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

    // @GetMapping("/mongodb/fetch")
    // public String fetchDataFromMongoDB(@RequestParam int userId, Model model) {
    //     Query query = new Query(Criteria.where("userId").is(userId));
    //     query.fields().include("data").include("title");
    //     query.fields().include("_id");
    //     DatabaseResponse data = new DatabaseResponse();
    //     data.setData(mongoTemplate.find(query, Object.class, "mealPlans"));
    //     // System.out.println(data.getData());
    //     model.addAttribute("responseData", data.getJsonData());
    //     return "response";

    // }
}