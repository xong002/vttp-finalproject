package project.vttpproject.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import project.vttpproject.exception.DuplicatePropertyException;
import project.vttpproject.exception.NotFoundException;
import project.vttpproject.exception.UpdateException;
import project.vttpproject.model.reviews.RentalReview;
import project.vttpproject.model.user.User;
import project.vttpproject.repository.RentalReviewRepository;
import project.vttpproject.repository.S3Repository;
import project.vttpproject.repository.UserRepository;

@Service
public class RentalReviewService {

    @Autowired
    private RentalReviewRepository reviewRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private S3Repository s3Repo;

    public Optional<RentalReview> getReviewById(String id) throws NotFoundException {
        return reviewRepo.getReviewById(id);
    }

    public List<RentalReview> getReviewsByPropertyId(Integer propId) throws NotFoundException {
        List<RentalReview> list = reviewRepo.getReviewsByPropertyId(propId);
        for (RentalReview r : list) {
            User user = userRepo.getUserById(r.getUserId()).get();
            r.setUser(user);
        }
        return list;
    }

    public List<RentalReview> getReviewsByUserId(Integer userId) throws NotFoundException {
        return reviewRepo.getReviewsByUserId(userId);
    }

    public List<RentalReview> getReviewsByBuildingName(String building, Integer propertyIdExcluded, Integer limit,
            Integer offset)
            throws NotFoundException {
        List<RentalReview> list = reviewRepo.getReviewsByBuildingName(building, propertyIdExcluded, limit, offset);
        for (RentalReview r : list) {
            User user = userRepo.getUserById(r.getUserId()).get();
            r.setUser(user);
        }
        return list;
    }

    @Transactional(rollbackFor = {
            IOException.class,
            UpdateException.class,
            DuplicatePropertyException.class })
    public String createNewReview(
            MultipartFile file,
            Integer userId,
            Integer propertyId,
            String title,
            String monthlyRentalCost,
            String floor,
            String apartmentFloorArea,
            String rentalFloorArea,
            String furnishings,
            Boolean sharedToilet,
            String rules,
            Date rentalStartDate,
            String rentalDuration,
            Integer occupants,
            BigDecimal rating,
            String comments,
            String status) throws UpdateException, IOException {
        RentalReview review = new RentalReview();
        review.setId(UUID.randomUUID().toString());
        review.setUserId(userId);
        review.setPropertyId(propertyId);
        review.setTitle(title);
        review.setMonthlyRentalCost(monthlyRentalCost);
        review.setFloor(floor);
        review.setApartmentFloorArea(apartmentFloorArea);
        review.setRentalFloorArea(rentalFloorArea);
        review.setFurnishings(furnishings);
        review.setSharedToilet(sharedToilet);
        review.setRules(rules);
        review.setRentalStartDate(rentalStartDate);
        review.setRentalDuration(rentalDuration);
        review.setOccupants(occupants);
        review.setRating(rating);
        review.setComments(comments);
        review.setStatus(status);
        User user = userRepo.getUserById(userId).get();
        String imageId = s3Repo.saveImage(file, user.getDisplayName());
        review.setImages(imageId);

        return reviewRepo.saveReview(review);
    }

    public Integer updateReview(RentalReview r) {
        return reviewRepo.updateReview(r);
    }
}
