package project.vttpproject.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsInput {
    private String email;
    private String password;
    private String role;
    private String status;
    private String displayName;

}
