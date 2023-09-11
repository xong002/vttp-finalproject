package project.vttpproject.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.json.Json;
import project.vttpproject.exception.UpdateException;
import project.vttpproject.model.user.UserInfo;
import project.vttpproject.model.user.AuthenticationRequest;
import project.vttpproject.model.user.AuthenticationResponse;
import project.vttpproject.model.user.UserDetailsInput;
import project.vttpproject.model.user.UserSummary;
import project.vttpproject.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<String> getUserById(@RequestParam Integer id) {
        Optional<UserSummary> opt = userService.getUserById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(404).body(
                    Json.createObjectBuilder().add("error", "user not found").build().toString());
        }
        return ResponseEntity.status(200).body(opt.get().toJson().toString());
    }

    // for registering
    @PostMapping("/create")
    public ResponseEntity<String> createNewUser(@RequestBody UserDetailsInput input) throws UpdateException, JsonProcessingException {
        AuthenticationResponse authResponse = userService.saveNewUser(input);
        
        ObjectMapper o = new ObjectMapper();
        String jsonString = o.writeValueAsString(authResponse);
        return ResponseEntity.status(200).body(jsonString);
    }

    // for logging in
    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request) throws JsonProcessingException{
        AuthenticationResponse authResponse = userService.authenticate(request);

        ObjectMapper o = new ObjectMapper();
        String jsonString = o.writeValueAsString(authResponse);
        return ResponseEntity.status(200).body(jsonString);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUserById(@RequestBody UserInfo userInfo, @RequestParam Integer id)
            throws UpdateException {
        Integer rowsUpdated = userService.updateUserInfo(userInfo, id);
        if (rowsUpdated <= 0)
            throw new UpdateException("user already exists");
        return ResponseEntity.status(200).body(Json.createObjectBuilder().add("updated", true).build().toString());
    }

    @PutMapping("/update/displayname")
    public ResponseEntity<String> updateUserDisplayeNameById(@RequestParam Integer id, @RequestParam String displayName)
            throws UpdateException {
        Integer rowsUpdated = userService.updateUserDisplayName(displayName, id);
        if (rowsUpdated <= 0)
            throw new UpdateException("display name already exists");
        return ResponseEntity.status(200).body(Json.createObjectBuilder().add("updated", true).build().toString());
    }



}
