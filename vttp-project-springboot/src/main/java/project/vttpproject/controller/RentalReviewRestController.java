package project.vttpproject.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.json.Json;
import project.vttpproject.exception.NotFoundException;
import project.vttpproject.exception.UpdateException;
import project.vttpproject.model.reviews.RentalReview;
import project.vttpproject.service.RentalReviewService;

@RestController
@RequestMapping("/api/review")
public class RentalReviewRestController {

    @Autowired
    private RentalReviewService reviewService;

    @GetMapping(params = "id")
    public ResponseEntity<String> getReviewById(@RequestParam String id)
            throws NotFoundException, JsonProcessingException {
        Optional<RentalReview> opt = reviewService.getReviewById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(404).body(
                    Json.createObjectBuilder().add("error", "review not found").build().toString());
        }
        ObjectMapper o = new ObjectMapper();
        String jsonString = o.writeValueAsString(opt.get());
        return ResponseEntity.status(200).body(jsonString);
    }

    @GetMapping(params = "propertyId")
    public ResponseEntity<String> getReviewsByPropertyId(@RequestParam Integer propertyId)
            throws NotFoundException, JsonProcessingException {
        List<RentalReview> list = reviewService.getReviewsByPropertyId(propertyId);
        ObjectMapper o = new ObjectMapper();
        String jsonString = o.writeValueAsString(list);
        return ResponseEntity.status(200).body(jsonString);
    }

    @GetMapping(params = "userId")
    public ResponseEntity<String> getReviewsByUserId(@RequestParam Integer userId)
            throws NotFoundException, JsonProcessingException {
        List<RentalReview> list = reviewService.getReviewsByUserId(userId);
        ObjectMapper o = new ObjectMapper();
        String jsonString = o.writeValueAsString(list);
        return ResponseEntity.status(200).body(jsonString);
    }

    @GetMapping(params = "building")
    public ResponseEntity<String> getReviewsByBuildingName(
            @RequestParam String building,
            @RequestParam Optional<Integer> optPropertyIdExcluded,
            @RequestParam Optional<Integer> optLimit,
            @RequestParam Optional<Integer> optOffset)
            throws NotFoundException, JsonProcessingException {
        Integer propertyIdExcluded = optPropertyIdExcluded.orElse(0);
        Integer limit = optLimit.orElse(5);
        Integer offset = optOffset.orElse(0);

        List<RentalReview> list = reviewService.getReviewsByBuildingName(building.toUpperCase(), propertyIdExcluded,
                limit, offset);
        ObjectMapper o = new ObjectMapper();
        String jsonString = o.writeValueAsString(list);
        return ResponseEntity.status(200).body(jsonString);
    }

    @PostMapping(path = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveReview(
            @RequestPart Optional<MultipartFile[]> files,
            @RequestPart String userId,
            @RequestPart String propertyId,
            @RequestPart String title,
            @RequestPart Optional<String> monthlyRentalCost,
            @RequestPart Optional<String> floor,
            @RequestPart Optional<String> apartmentFloorArea,
            @RequestPart Optional<String> rentalFloorArea,
            @RequestPart Optional<String> furnishings,
            @RequestPart Optional<String> sharedToilet,
            @RequestPart Optional<String> rules,
            @RequestPart Optional<String> rentalStartDate,
            @RequestPart Optional<String> rentalDuration,
            @RequestPart Optional<String> occupants,
            @RequestPart String rating,
            @RequestPart String comments,
            @RequestPart String status) throws UpdateException, IOException {

        String generatedUserId = reviewService.createNewReview(
                files,
                userId,
                propertyId,
                title,
                monthlyRentalCost,
                floor,
                apartmentFloorArea,
                rentalFloorArea,
                furnishings,
                sharedToilet,
                rules,
                rentalStartDate,
                rentalDuration,
                occupants,
                rating,
                comments,
                status);

        return ResponseEntity.status(201)
                .body(Json.createObjectBuilder().add("generatedReviewId", generatedUserId).build().toString());
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateReview(@RequestBody RentalReview review) throws NotFoundException {
        Integer updateCount = reviewService.updateReview(review);
        System.out.println(updateCount);
        if (updateCount == 0)
            throw new NotFoundException("review id " + review.getId() + " not found");
        return ResponseEntity.status(202)
                .body(Json.createObjectBuilder().add("updated", true).build().toString());
    }
}
