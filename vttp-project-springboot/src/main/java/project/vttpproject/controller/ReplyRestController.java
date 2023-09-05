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
import project.vttpproject.model.replies.Reply;
import project.vttpproject.service.ReplyService;

@RestController
@RequestMapping("/api/reply")
public class ReplyRestController {
    
    @Autowired
    private ReplyService replyService;

    @GetMapping(params = "id")
    public ResponseEntity<String> getReplyById(@RequestParam String id) throws UpdateException, JsonProcessingException{
        Optional<Reply> opt = replyService.getReplyById(id);
        if (opt.isEmpty()){
            return ResponseEntity.status(404).body(
                    Json.createObjectBuilder().add("error", "reply not found").build().toString());
        }
        ObjectMapper o = new ObjectMapper();
        String jsonString = o.writeValueAsString(opt.get());
        return ResponseEntity.status(200).body(jsonString);     
    }

    @GetMapping(params = "reviewId")
    public ResponseEntity<String> getRepliesByReviewId(@RequestParam String reviewId) throws UpdateException, JsonProcessingException{
        List<Reply> list = replyService.getRepliesByReviewId(reviewId);
        ObjectMapper o = new ObjectMapper();
        String jsonString = o.writeValueAsString(list);
        return ResponseEntity.status(200).body(jsonString);    
    }

    @GetMapping(params = "userId")
    public ResponseEntity<String> getRepliesByUserId(@RequestParam Integer userId) throws UpdateException, JsonProcessingException{
        List<Reply> list = replyService.getRepliesByUserId(userId);
        ObjectMapper o = new ObjectMapper();
        String jsonString = o.writeValueAsString(list);
        return ResponseEntity.status(200).body(jsonString);    
    }

    @PostMapping("/create")
    public ResponseEntity<String> saveReply(@RequestBody Reply reply) throws UpdateException {
        String generatedUserId = replyService.createNewReply(reply);
        return ResponseEntity.status(201)
                .body(Json.createObjectBuilder().add("generatedReplyId", generatedUserId).build().toString());
    }
}
