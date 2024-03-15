package com.dietify.v1.Controllers;

import java.security.Principal;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dietify.v1.DTO.Day.DayResponse;
import com.dietify.v1.DTO.Week.WeekResponse;
import com.dietify.v1.Entity.Favourite;
import com.dietify.v1.Entity.User;
import com.dietify.v1.Repository.UserRepo;
import com.dietify.v1.Service.FavService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;

@Controller
public class MongoDBController {
    @Autowired
    private UserRepo userRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private FavService favService;

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

            mongoTemplate.save(document, "mealPlans");
            ObjectId savedDocumentId = document.getObjectId("_id");

            favService.saveFavourite(user, savedDocumentId, "day", userText);

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

            mongoTemplate.save(document, "mealPlans");
            ObjectId savedDocumentId = document.getObjectId("_id");

            favService.saveFavourite(user, savedDocumentId, "week", userText);

            return ResponseEntity.ok().body("Data stored successfully for user " + user.getId());
        } catch (JsonParseException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid JSON format.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error storing data.");
        }
    }

    @GetMapping("/userprofile")
    public String retrieveDataByUserIdAndType(Principal p, Model m) {
        try {
            String email = p.getName();
            User user = userRepo.findByEmail(email);
            m.addAttribute("name", user.getName());

            List<Favourite> dayFavourites = favService.findByUserIdAndType(user.getId(), "day");
            List<Favourite> weekFavourites = favService.findByUserIdAndType(user.getId(), "week");

            if (dayFavourites.isEmpty() && weekFavourites.isEmpty()) {
                m.addAttribute("empty", "No saved DAY or WEEK PLANS found");
            }

            m.addAttribute("weekfavs", weekFavourites);
            m.addAttribute("dayfavs", dayFavourites);
            return "profile";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @PostMapping("/savedDayPlan")
    public String fetchDayPlan(@RequestParam String favouriteId, Model model) {
        try {
            ObjectId objectId = new ObjectId(favouriteId);
            Document document = mongoTemplate.findById(objectId, Document.class, "mealPlans");
            if (document == null) {
                return "error";
            }
            model.addAttribute("dayResponse", document);
            return "MealViews/day-list";

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid
            // favouriteId format.");
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error
            // fetching data.");
            return "error";
        }
        // return "error";
    }

    @PostMapping("/savedWeekPlan")
    public String fetchWeekPlan(@RequestParam String favouriteId, Model model) {
        try {
            ObjectId objectId = new ObjectId(favouriteId);
            Document document = mongoTemplate.findById(objectId, Document.class, "mealPlans");
            if (document == null) {
                return "error";
            }
            model.addAttribute("weekresponse", document);
            return "MealViews/weekList";
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid
            // favouriteId format.");
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error
            // fetching data.");
            return "error";
        }
    }

}