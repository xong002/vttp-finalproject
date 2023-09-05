package project.vttpproject.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.vttpproject.exception.UpdateException;
import project.vttpproject.model.reviews.RentalReview;
import project.vttpproject.repository.RentalReviewRepository;

@Service
public class RentalReviewService {
    
    @Autowired
    private RentalReviewRepository reviewRepo;

    public Optional<RentalReview> getReviewById(String id) throws UpdateException{
        return reviewRepo.getReviewById(id);
    }

    public List<RentalReview> getReviewsByPropertyId(Integer propId) throws UpdateException{
        return reviewRepo.getReviewsByPropertyId(propId);
    }
    
    public List<RentalReview> getReviewsByUserId(Integer userId) throws UpdateException{
        return reviewRepo.getReviewsByUserId(userId);
    }

    public String createNewReview(RentalReview review) throws UpdateException{
        review.setId(UUID.randomUUID().toString());
        return reviewRepo.saveReview(review);
    }

}
