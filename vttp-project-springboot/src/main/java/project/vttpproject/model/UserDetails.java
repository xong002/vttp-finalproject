package project.vttpproject.model;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {
    private Integer id;
    private String email;
    private String password;
    private String role;
    private String status;
    private Timestamp createdDate;

    
}
