package project.vttpproject.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import project.vttpproject.exception.DuplicatePropertyException;
import project.vttpproject.exception.UpdateException;
import project.vttpproject.model.property.Property;

@Repository
public class PropertyRepository {

    private final String GET_PROPERTY_BY_ID_SQL = "select * from properties where id = ?";
    private final String CREATE_PROPERTY_SQL = "insert into properties (area_id, images, building, blk_no, road_name, postal, latitude, longitude, highest_floor) values (?,?,?,?,?,?,?,?,?)";
    private final String GET_PROPERTYID_BY_POSTAL_SQL = "select id from properties where postal = ?";
    private final String GET_PROPERTY_BY_TEXT_SQL = "select * from properties where concat_ws(building,' ',road_name,' ',postal) LIKE ? order by id ASC";

    @Autowired
    private JdbcTemplate template;

    public Optional<Property> getPropertyById(Integer id) throws UpdateException {
        BeanPropertyRowMapper<Property> newInstance = BeanPropertyRowMapper.newInstance(Property.class);
        newInstance.setPrimitivesDefaultedForNullValue(true);

        try {
            Property response = template.queryForObject(
                    GET_PROPERTY_BY_ID_SQL,
                    newInstance,
                    id);
            if (response == null)
                throw new UpdateException("property not found");
            return Optional.of(response);
        } catch (DataAccessException e) {
            throw new UpdateException("property not found");
        }
    }

    public List<Property> searchPropertyByText(String searchVal) {
        String text = "%" + searchVal + "%";
        System.out.println(">>>>>>>>>>>>>" + text);

        BeanPropertyRowMapper<Property> newInstance = BeanPropertyRowMapper.newInstance(Property.class);
        newInstance.setPrimitivesDefaultedForNullValue(true);

        List<Property> response = template.query(
                GET_PROPERTY_BY_TEXT_SQL,
                newInstance,
                text);
        return response;
    }

    public Integer saveProperty(Property p) throws UpdateException, DuplicatePropertyException {
        KeyHolder generatedKey = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(CREATE_PROPERTY_SQL, new String[] { "id" });
                ps.setInt(1, p.getAreaId() == null ? 0 : p.getAreaId());
                ps.setString(2, p.getImages());
                ps.setString(3, p.getBuilding());
                ps.setString(4, p.getBlkNo());
                ps.setString(5, p.getRoadName());
                ps.setString(6, p.getPostal());
                ps.setBigDecimal(7, p.getLatitude());
                ps.setBigDecimal(8, p.getLongitude());
                ps.setString(9, p.getHighestFloor());
                return ps;
            }
        };
        try {
            int rowsUpdated = template.update(psc, generatedKey);
            if (rowsUpdated <= 0)
                throw new UpdateException("property already exists");
            return generatedKey.getKey().intValue();
        } catch (DuplicateKeyException ex) {
            return template.queryForObject(
                    GET_PROPERTYID_BY_POSTAL_SQL,
                    Integer.class,
                    p.getPostal());
        }
    }

    private final String UPDATE_PROPERTY_BY_ID = "update properties set area_id = ?, images = ?, building = ?, blk_no= ? , road_name = ?, postal = ?, latitude = ?, longitude = ?, highest_floor = ? where id = ?";

    public Integer updateProperty(Property p) {
        return template.update(UPDATE_PROPERTY_BY_ID, p.getAreaId(), p.getImages(), p.getBuilding(), p.getBlkNo(),
                p.getRoadName(), p.getPostal(), p.getLatitude(), p.getLongitude(), p.getHighestFloor(), p.getId());
    }
}
