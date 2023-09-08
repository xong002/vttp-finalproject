package project.vttpproject.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.json.Json;
import project.vttpproject.exception.DuplicatePropertyException;
import project.vttpproject.exception.UpdateException;
import project.vttpproject.model.property.Property;
import project.vttpproject.service.PropertyService;

@RestController
@RequestMapping("/api/property")
public class PropertyRestController {

    @Autowired
    private PropertyService propService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getPropertyById(@RequestParam Integer id)
            throws UpdateException, JsonProcessingException {
        Optional<Property> opt = propService.getPropertyById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(404).body(
                    Json.createObjectBuilder().add("error", "property not found").build().toString());
        }
        ObjectMapper o = new ObjectMapper();
        String jsonString = o.writeValueAsString(opt.get());
        return ResponseEntity.status(200).body(jsonString);
    }

    @PostMapping(path = "/create", params = "returnProperty", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createProperty(@RequestBody Property p, @RequestParam Optional<String> returnProperty)
            throws UpdateException, DuplicatePropertyException, JsonProcessingException {

        Integer generatedUserId = propService.createNewProperty(p);

        if (!returnProperty.isPresent() || returnProperty.get().equals("N")) {
            return ResponseEntity.status(201)
                    .body(Json.createObjectBuilder().add("generatedPropertyId", generatedUserId).build().toString());
        } else if (returnProperty.get().equals("Y")) {
            Optional<Property> opt = propService.getPropertyById(generatedUserId);
            if (opt.isEmpty()) {
                return ResponseEntity.status(404).body(
                        Json.createObjectBuilder().add("error", "property not found").build().toString());
            }
            ObjectMapper o = new ObjectMapper();
            String jsonString = o.writeValueAsString(opt.get());
            return ResponseEntity.status(200).body(jsonString);
        }
        return ResponseEntity.status(400).body(
                Json.createObjectBuilder().add("error", "invalid value for returnProperty (Y/N only)").build()
                        .toString());
    }

    @PostMapping(path = "/createbatch", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createProperties(@RequestBody List<Property> list)
            throws UpdateException, DuplicatePropertyException, JsonProcessingException {
        List<Property> propList = propService.createNewProperties(list);
        ObjectMapper o = new ObjectMapper();
        String jsonString = o.writeValueAsString(propList);
        return ResponseEntity.status(200).body(jsonString);
    }

    // TODO: add file upload
    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> uploadPropertyImageFile(
            @RequestPart MultipartFile file,
            @RequestPart String userName,
            @RequestPart String propertyId) throws IOException, UpdateException, DuplicatePropertyException {

        propService.saveImage(file, userName, Integer.valueOf(propertyId));

        return ResponseEntity.status(202).build();
    }
}
