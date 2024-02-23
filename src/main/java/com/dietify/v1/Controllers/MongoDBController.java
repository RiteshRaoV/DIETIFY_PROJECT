package com.dietify.v1.Controllers;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MongoDBController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostMapping("mongodb/store")
    public ResponseEntity<String> storeDataToMongoDB(@RequestBody String body, @RequestParam String userID) {
        try {
            // Convert the JSON string to a Document
            Document document = Document.parse(body);

            // Add the userID field to the Document
            document.append("userID", userID);

            // Save the Document to MongoDB
            mongoTemplate.save(document, "mealPlans");

            return new ResponseEntity<>("Data stored successfully for user " + userID, HttpStatus.OK);
        } catch (Exception e) {
            // Handle exception if there's an issue with parsing or saving
            return new ResponseEntity<>("Error storing data for user " + userID, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/mongodb/fetch")
    public ResponseEntity<String> fetchDataFromMongoDB(@RequestParam String userID) {
        String data = mongoTemplate.findAll(String.class, "mealPlans" ).toString();
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}