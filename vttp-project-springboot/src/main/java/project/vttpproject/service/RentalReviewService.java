package project.vttpproject.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${s3.bucket.url}")
    private String s3URL;

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
            Optional<MultipartFile[]> files,
            String userId,
            String propertyId,
            String title,
            Optional<String> monthlyRentalCost,
            Optional<String> floor,
            Optional<String> apartmentFloorArea,
            Optional<String> rentalFloorArea,
            Optional<String> furnishings,
            Optional<String> sharedToilet,
            Optional<String> rules,
            Optional<String> rentalStartDate,
            Optional<String> rentalDuration,
            Optional<String> occupants,
            String rating,
            String comments,
            String status) throws UpdateException, IOException {
        RentalReview review = new RentalReview();
        review.setId(UUID.randomUUID().toString());
        review.setUserId(Integer.valueOf(userId));
        review.setPropertyId(Integer.valueOf(propertyId));
        review.setTitle(title);
        review.setMonthlyRentalCost(monthlyRentalCost.isEmpty() ? "" : monthlyRentalCost.get());
        review.setFloor(floor.isEmpty() ? "" : floor.get());
        review.setApartmentFloorArea(apartmentFloorArea.isEmpty() ? "" : apartmentFloorArea.get());
        review.setRentalFloorArea(rentalFloorArea.isEmpty() ? "" : rentalFloorArea.get());
        review.setFurnishings(furnishings.isEmpty() ? "" : furnishings.get());
        review.setSharedToilet(sharedToilet.isEmpty() ? null : Boolean.valueOf(sharedToilet.get()));
        review.setRules(rules.isEmpty() ? "" : rules.get());
        review.setRentalStartDate(rentalStartDate.isEmpty() ? null : Date.valueOf(rentalStartDate.get()));
        review.setRentalDuration(rentalDuration.isEmpty() ? "" : rentalDuration.get());
        review.setOccupants(Integer.valueOf(occupants.get()));
        review.setRating(new BigDecimal(rating));
        review.setComments(comments);
        review.setStatus(status);

        if (!files.isEmpty()) {
            
            MultipartFile[] filesArr = files.get();
            User user = userRepo.getUserById(Integer.valueOf(userId)).get();
            StringBuilder imagesString = new StringBuilder();
            
            for (MultipartFile f : filesArr){
                String imageId = s3Repo.saveImage(f, user.getDisplayName());
                imagesString.append(" " + s3URL + imageId);
                System.out.println(imagesString.toString());
            }
            
            review.setImages(imagesString.toString());
        }

        return reviewRepo.saveReview(review);
    }

    public Integer updateReview(RentalReview r) {
        return reviewRepo.updateReview(r);
    }
}
