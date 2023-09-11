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
    private UserInfo userInfo;

    public JsonObject toJson() {
        JsonObject jsonObj = Json.createObjectBuilder()
                .add("userId", this.user.getId())
                .add("displayName", this.user.getDisplayName())
                .add("email", this.userInfo.getEmail())
                .add("password", this.userInfo.getPassword())
                .add("role", this.userInfo.getRole().toString())
                .add("status", this.userInfo.getStatus())
                .add("createdDate", this.userInfo.getCreatedDate().toString())
                .build();
        return jsonObj;
    }
}
