package project.vttpproject.model;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private Integer userDetailsId;
    private String displayName;

    public JsonObject toJson() {
        JsonObject jsonObj = Json.createObjectBuilder()
                .add("id", this.id)
                .add("userDetailsId", this.userDetailsId)
                .add("displayName", this.displayName)
                .build();
        return jsonObj;
    }
}
