package project.vttpproject.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import project.vttpproject.exception.DuplicatePropertyException;
import project.vttpproject.exception.UpdateException;
import project.vttpproject.model.property.Property;
import project.vttpproject.repository.PropertyRepository;
import project.vttpproject.repository.S3Repository;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propRepo;

    @Autowired
    private S3Repository s3Repo;

    @Value("${s3.bucket.url}")
    private String s3URL;

    public Optional<Property> getPropertyById(Integer id) throws UpdateException {
        return propRepo.getPropertyById(id);
    }

    public Integer createNewProperty(Property p) throws UpdateException, DuplicatePropertyException {
        // TODO: check for null values in Property p
        return propRepo.saveProperty(p);
    }

    @Transactional(rollbackFor = { SQLException.class, UpdateException.class, DuplicatePropertyException.class })
    public List<Property> createNewProperties(List<Property> list) throws UpdateException, DuplicatePropertyException {
        List<Property> newList = new LinkedList<>();
        for (Property p : list) {
            Integer generatedId = propRepo.saveProperty(p);
            Optional<Property> opt = propRepo.getPropertyById(generatedId);
            newList.add(opt.get());
        }
        return newList;
    }

    @Transactional(rollbackFor = { IOException.class, UpdateException.class, DuplicatePropertyException.class })
    public Integer saveImage(MultipartFile file, String userName, Integer propertyId)
            throws IOException, UpdateException, DuplicatePropertyException {
        String imageId = s3Repo.saveImage(file, userName);
        Property p = propRepo.getPropertyById(propertyId).get();
        p.setImages(s3URL + imageId);
        return propRepo.updateProperty(p);
    }

}
