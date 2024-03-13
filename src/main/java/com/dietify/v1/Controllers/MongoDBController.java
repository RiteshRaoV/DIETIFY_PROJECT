package com.dietify.v1.Controllers;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
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
import com.dietify.v1.Entity.Favourite;
import com.dietify.v1.Entity.User;
import com.dietify.v1.Repository.FavouriteRepo;
import com.dietify.v1.Repository.UserRepo;
import com.dietify.v1.Service.FavService;
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

    // @GetMapping("/mongodb/fetch")
    // public String fetchDataFromMongoDB(@RequestParam int userId, Model model) {
    // Query query = new Query(Criteria.where("userId").is(userId));
    // query.fields().include("data").include("title");
    // query.fields().include("_id");
    // DatabaseResponse data = new DatabaseResponse();
    // data.setData(mongoTemplate.find(query, Object.class, "mealPlans"));
    // // System.out.println(data.getData());
    // model.addAttribute("responseData", data.getJsonData());
    // return "response";

    // }
    @GetMapping("/userprofile")
    public String retrieveDataByUserIdAndType(Principal p, Model m) {
        try {
            String email = p.getName();
            User user = userRepo.findByEmail(email);
            m.addAttribute("name", user.getName());

            List<Favourite> dayFavourites = favService.findByUserIdAndType(user.getId(), "day");
            List<Favourite> weekFavourites = favService.findByUserIdAndType(user.getId(), "week");

            // if (dayFavourites.isEmpty() && weekFavourites.isEmpty()) {
            // return ResponseEntity.status(HttpStatus.NOT_FOUND)
            // .body("No saved DAY or WEEK PLANS found for userId: " + user.getId());
            // }

            List<DayResponse> dayResponses = new ArrayList<>();
            List<WeekResponse> weekResponses = new ArrayList<>();

            for (Favourite dayFavourite : dayFavourites) {
                ObjectId dayFavouriteId = new ObjectId(dayFavourite.getFavouriteId());
                Document dayPlanDocument = mongoTemplate.findById(dayFavouriteId, Document.class, "mealPlans");
                if (dayPlanDocument != null) {
                    DayResponse dayResponse = convertDocumentToDayResponse(dayPlanDocument);
                    dayResponses.add(dayResponse);
                }
            }

            for (Favourite weekFavourite : weekFavourites) {
                ObjectId weekFavouriteId = new ObjectId(weekFavourite.getFavouriteId());
                Document weekPlanDocument = mongoTemplate.findById(weekFavouriteId, Document.class, "mealPlans");
                if (weekPlanDocument != null) {
                    WeekResponse weekResponse = convertDocumentToWeekResponse(weekPlanDocument);
                    weekResponses.add(weekResponse);
                }
            }

            // if (dayResponses.isEmpty() && weekResponses.isEmpty()) {
            // return ResponseEntity.status(HttpStatus.NOT_FOUND)
            // .body("No meal plans found for DAY or WEEK PLANS for userId: " +
            // user.getId());
            // }
            m.addAttribute(weekResponses);
            m.addAttribute(dayResponses);
            // Process dayResponses and weekResponses as needed...
            return "profile";
            // return ResponseEntity.ok().body("Day Responses: " + dayResponses.toString()
            // + "\nWeek Responses: " + weekResponses.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
            // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error
            // retrieving data.");
        }
    }

    private DayResponse convertDocumentToDayResponse(Document document) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(document.toJson(), DayResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception appropriately
            return null;
        }
    }

    private WeekResponse convertDocumentToWeekResponse(Document document) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(document.toJson(), WeekResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception appropriately
            return null;
        }
    }

}