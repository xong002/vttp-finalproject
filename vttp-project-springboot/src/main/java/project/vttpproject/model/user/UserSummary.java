package project.vttpproject.model.user;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSummary {
    private User user;
    private UserDetails userDetails;

    public JsonObject toJson() {
        JsonObject jsonObj = Json.createObjectBuilder()
                .add("userId", this.user.getId())
                .add("displayName", this.user.getDisplayName())
                .add("email", this.userDetails.getEmail())
                .add("password", this.userDetails.getPassword())
                .add("role", this.userDetails.getRole())
                .add("status", this.userDetails.getStatus())
                .add("createdDate", this.userDetails.getCreatedDate().toString())
                .build();
        return jsonObj;
    }
}
