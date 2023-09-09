package project.vttpproject.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.json.Json;
import project.vttpproject.exception.UpdateException;
import project.vttpproject.model.reviews.RentalReview;
import project.vttpproject.service.RentalReviewService;

@RestController
@RequestMapping("/api/review")
public class RentalReviewRestController {
    
    @Autowired
    private RentalReviewService reviewService;

    @GetMapping(params = "id")
    public ResponseEntity<String> getReviewById(@RequestParam String id) throws UpdateException, JsonProcessingException{
        Optional<RentalReview> opt = reviewService.getReviewById(id);
        if (opt.isEmpty()){
            return ResponseEntity.status(404).body(
                    Json.createObjectBuilder().add("error", "review not found").build().toString());
        }
        ObjectMapper o = new ObjectMapper();
        String jsonString = o.writeValueAsString(opt.get());
        return ResponseEntity.status(200).body(jsonString);        
    }

    @GetMapping(params = "propertyId")
    public ResponseEntity<String> getReviewsByPropertyId(@RequestParam Integer propertyId) throws UpdateException, JsonProcessingException{
        List<RentalReview> list = reviewService.getReviewsByPropertyId(propertyId);
        ObjectMapper o = new ObjectMapper();
        String jsonString = o.writeValueAsString(list);
        return ResponseEntity.status(200).body(jsonString);        
    }

    @GetMapping(params = "userId")
    public ResponseEntity<String> getReviewsByUserId(@RequestParam Integer userId) throws UpdateException, JsonProcessingException{
        List<RentalReview> list = reviewService.getReviewsByUserId(userId);
        ObjectMapper o = new ObjectMapper();
        String jsonString = o.writeValueAsString(list);
        return ResponseEntity.status(200).body(jsonString);        
    }

    @PostMapping("/create")
    public ResponseEntity<String> saveReview(@RequestBody RentalReview review) throws UpdateException {
        String generatedUserId = reviewService.createNewReview(review);
        return ResponseEntity.status(201)
                .body(Json.createObjectBuilder().add("generatedReviewId", generatedUserId).build().toString());
    }
}
