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
import project.vttpproject.model.property.Property;
import project.vttpproject.service.PropertyService;

@RestController
@RequestMapping("/api/property")
public class PropertyRestController {

    @Autowired
    private PropertyService propService;

    @GetMapping
    public ResponseEntity<String> getPropertyById(@RequestParam Integer id) throws UpdateException {
        Optional<Property> opt = propService.getPropertyById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(404).body(
                    Json.createObjectBuilder().add("error", "property not found").build().toString());
        }
        return ResponseEntity.status(200).body(opt.get().toJson().toString());
    }

    // TODO: add file upload
    @PostMapping("/create")
    public ResponseEntity<String> createProperty(@RequestBody Property p) throws UpdateException {
        Integer generatedUserId = propService.createNewProperty(p);
        return ResponseEntity.status(201)
                .body(Json.createObjectBuilder().add("generatedPropertyId", generatedUserId).build().toString());
    }

}
