package project.vttpproject.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import project.vttpproject.exception.UpdateException;
import project.vttpproject.model.property.Property;

@Repository
public class PropertyRepository {

    private final String GET_PROPERTY_BY_ID_SQL = "select * from properties where id = ?";
    private final String CREATE_PROPERTY_BY_ID_SQL = "insert into properties (area_id, images, building, blk_no, road_name, postal, latitude, longitude, highest_floor) values (?,?,?,?,?,?,?,?,?)";

    @Autowired
    private JdbcTemplate template;

    public Optional<Property> getPropertyById(Integer id) {
        BeanPropertyRowMapper<Property> newInstance = BeanPropertyRowMapper.newInstance(Property.class);
        newInstance.setPrimitivesDefaultedForNullValue(true);
        try {
            Property response = template.queryForObject(
                    GET_PROPERTY_BY_ID_SQL,
                    newInstance,
                    id);
            if (response == null)
                return Optional.empty();
            return Optional.of(response);
        } catch (DataAccessException e) {
            return Optional.empty();
        }

    }

    public Integer saveProperty(Property p) throws UpdateException{
        KeyHolder generatedKey = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(CREATE_PROPERTY_BY_ID_SQL, new String[] { "id" });
                ps.setInt(1, p.getAreaId());
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
        int rowsUpdated = template.update(psc, generatedKey);
        if (rowsUpdated <= 0) throw new UpdateException("property already exists");
        return generatedKey.getKey().intValue();
    }

}
