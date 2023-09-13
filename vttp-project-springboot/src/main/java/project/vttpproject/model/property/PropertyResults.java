package project.vttpproject.model.property;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropertyResults {
    private Integer found;
    private Integer totalNumPages;
    private Integer pageNum;
    private List<Property> results;

    public static PropertyResults create(String json) throws IOException {
        PropertyResults p = new PropertyResults();
        p.setResults(new LinkedList<>());
        if (json!=null){
            try(InputStream is = new ByteArrayInputStream(json.getBytes())){
                JsonReader r = Json.createReader(is);
                JsonObject o = r.readObject();

                p.setFound(o.getInt("found"));
                p.setTotalNumPages(o.getInt("totalNumPages"));
                p.setPageNum(o.getInt("pageNum"));
                JsonArray arr = o.getJsonArray("results");
                for(JsonValue v : arr){
                    Property property = new Property();
                    property.setBlkNo(v.asJsonObject().getString("BLK_NO"));
                    property.setRoadName(v.asJsonObject().getString("ROAD_NAME"));
                    property.setBuilding(v.asJsonObject().getString("BUILDING"));
                    property.setPostal(v.asJsonObject().getString("POSTAL"));
                    property.setLatitude(new BigDecimal(v.asJsonObject().getString("LATITUDE")));
                    property.setLongitude(new BigDecimal(v.asJsonObject().getString("LONGITUDE")));
                    p.getResults().add(property);
                    // System.out.println(">>>>>" +  property.toString());
                }

            }
        }
        return p;
    }
}
