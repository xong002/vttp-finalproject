package project.vttpproject.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import project.vttpproject.exception.UpdateException;
import project.vttpproject.model.User;
import project.vttpproject.model.UserDetailsInput;
import project.vttpproject.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<String> getUserById(@RequestParam Integer id) {
        Optional<User> opt = userService.getUserById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(404).body(
                    Json.createObjectBuilder().add("error", "user not found").build().toString());
        }
        return ResponseEntity.status(200).body(opt.get().toJson().toString());
    }

    @PostMapping("/create")
    public ResponseEntity<String> createNewUser(@RequestBody UserDetailsInput input) throws UpdateException {
        Integer generatedUserId = userService.saveNewUser(input);
        return ResponseEntity.status(201)
                .body(Json.createObjectBuilder().add("generatedUserId", generatedUserId).build().toString());
    }

}
