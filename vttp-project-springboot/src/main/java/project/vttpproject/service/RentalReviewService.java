package project.vttpproject.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.vttpproject.exception.UpdateException;
import project.vttpproject.model.reviews.RentalReview;
import project.vttpproject.model.user.User;
import project.vttpproject.repository.RentalReviewRepository;
import project.vttpproject.repository.UserRepository;

@Service
public class RentalReviewService {
    
    @Autowired
    private RentalReviewRepository reviewRepo;

    @Autowired
    private UserRepository userRepo;

    public Optional<RentalReview> getReviewById(String id) throws UpdateException{
        return reviewRepo.getReviewById(id);
    }

    public List<RentalReview> getReviewsByPropertyId(Integer propId) throws UpdateException{
        List<RentalReview> list =  reviewRepo.getReviewsByPropertyId(propId);
        for (RentalReview r : list){
            User user= userRepo.getUserById(r.getUserId()).get();
            r.setUser(user);
        }
        return list;
    }
    
    public List<RentalReview> getReviewsByUserId(Integer userId) throws UpdateException{
        return reviewRepo.getReviewsByUserId(userId);
    }

    public String createNewReview(RentalReview review) throws UpdateException{
        review.setId(UUID.randomUUID().toString());
        return reviewRepo.saveReview(review);
    }

}
