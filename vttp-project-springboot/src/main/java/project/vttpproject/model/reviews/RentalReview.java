package project.vttpproject.model.reviews;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.vttpproject.model.user.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalReview {
    private String id;
    private Integer userId;
    private Integer propertyId;
    private String title;
    private String monthlyRentalCost;
    private String floor;
    private String apartmentFloorArea;
    private String rentalFloorArea;
    private String furnishings;
    private Boolean sharedToilet;
    private String rules;
    private Date rentalStartDate;
    private String rentalDuration;
    private Integer occupants;
    private BigDecimal rating;
    private String comments;
    private String status;
    private Timestamp createdDate;
    private Timestamp updatedAt;
    private User user;
}
