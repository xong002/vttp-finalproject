package project.vttpproject.model.user;

import java.io.StringReader;
import java.sql.Timestamp;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
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

    // public static UserDetails createUserDetails(String jsonString) {
    //     UserDetails ud = new UserDetails();
    //     JsonReader r = Json.createReader(new StringReader(jsonString));
    //     JsonObject o = r.readObject();
    //     ud.setEmail(o.getString("email"));
    //     ud.setPassword(o.getString("password"));
    //     ud.setRole(o.getString("role"));
    //     ud.setStatus(o.getString("status"));
    //     return ud;
    // }

}
