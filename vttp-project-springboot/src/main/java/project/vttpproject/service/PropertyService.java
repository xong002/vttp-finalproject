package project.vttpproject.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.vttpproject.exception.UpdateException;
import project.vttpproject.model.property.Property;
import project.vttpproject.repository.PropertyRepository;

@Service
public class PropertyService {
    
    @Autowired
    private PropertyRepository propRepo;

    public Optional<Property> getPropertyById(Integer id) throws UpdateException{
        return propRepo.getPropertyById(id);
    }

    public Integer createNewProperty(Property p) throws UpdateException{
        //TODO: check for null values in Property p
        return propRepo.saveProperty(p);
    }

}
