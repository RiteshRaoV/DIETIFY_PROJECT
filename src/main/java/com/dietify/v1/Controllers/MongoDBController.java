package com.dietify.v1.Controllers;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dietify.v1.DTO.Day.DayResponse;
import com.dietify.v1.DTO.Week.WeekResponse;
import com.dietify.v1.Entity.User;
import com.dietify.v1.Repository.UserRepo;
import com.dietify.v1.Service.FavService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/favourites")
public class MongoDBController {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private FavService favService;

    @ModelAttribute("controllerName")
    public String getControllerName() {
        return "MongoDBController";
    }

    @PostMapping("/store-day")
    public String storeDataToMongoDB(@RequestParam String titleInput, HttpSession session) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = authentication.getName();
            User user = userRepository.findByEmail(userEmail);
            if (user == null) {
                return "error";
            }

            DayResponse dayResponse = (DayResponse) session.getAttribute("dayResponse");
            if (dayResponse == null) {
                return "error";
            }

            ObjectMapper objectMapper = new ObjectMapper();
            String dayResponseJson = objectMapper.writeValueAsString(dayResponse);

            Document document = Document.parse(dayResponseJson);

            mongoTemplate.save(document, "mealPlans");
            ObjectId savedDocumentId = document.getObjectId("_id");

            favService.saveFavourite(user, savedDocumentId, "day", titleInput);

            return "redirect:/mealplanner/day";
        } catch (JsonParseException e) {
            e.printStackTrace();
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @PostMapping("/store-week")
    public String storeWeekDataToMongoDB(@RequestParam String titleInput, HttpSession session) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = authentication.getName();
            User user = userRepository.findByEmail(userEmail);
            if (user == null) {
                return "error";
            }

            WeekResponse weekResponse = (WeekResponse) session.getAttribute("weekresponse");
            if (weekResponse == null) {
                return "error";
            }

            ObjectMapper objectMapper = new ObjectMapper();
            String weekResponseJson = objectMapper.writeValueAsString(weekResponse);

            Document document = Document.parse(weekResponseJson);

            mongoTemplate.save(document, "mealPlans");
            ObjectId savedDocumentId = document.getObjectId("_id");

            favService.saveFavourite(user, savedDocumentId, "week", titleInput);

            return "redirect:/mealplanner/week";
        } catch (JsonParseException e) {
            e.printStackTrace();
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @PostMapping("/day")
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
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @PostMapping("/week")
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
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}
