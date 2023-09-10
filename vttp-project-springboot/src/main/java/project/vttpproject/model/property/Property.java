package project.vttpproject.model.property;

import java.math.BigDecimal;
import java.sql.Timestamp;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Property {
    private Integer id;
    private Integer areaId;
    private String images;
    private String building;
    private String blkNo;
    private String roadName;
    private String postal;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String highestFloor;
    private Timestamp createdDate;
    private Timestamp updatedAt;
    private Integer reviewCount;

    // public JsonObject toJson() {
    //     System.out.println();
    //     JsonObject jsonObj = Json.createObjectBuilder()
    //             .add("id", this.id)
    //             .add("areaId", (this.areaId == null ?  0 : this.areaId))
    //             .add("images", (this.images == null ?  "" : this.images))
    //             .add("building", this.building)
    //             .add("blkNo", this.blkNo)
    //             .add("roadName", this.roadName)
    //             .add("postal", this.postal)
    //             .add("latitude", this.latitude)
    //             .add("longitude", this.longitude)
    //             .add("highestFloor", this.highestFloor)
    //             .add("createdDate", this.createdDate.toString())
    //             .add("updatedAt", this.updatedAt.toString())
    //             .build();
    //     return jsonObj;
    // }
}
