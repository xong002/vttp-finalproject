package project.vttpproject.model.replies;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reply {
    private String id;
    private Integer userId;
    private String rentalReviewsId;
    private String comments;
    private String status;
    private Timestamp createdDate;
    private Timestamp updatedAt;
}
