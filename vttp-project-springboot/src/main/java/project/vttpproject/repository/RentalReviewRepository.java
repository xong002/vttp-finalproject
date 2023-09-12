package project.vttpproject.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import project.vttpproject.exception.NotFoundException;
import project.vttpproject.exception.UpdateException;
import project.vttpproject.model.reviews.RentalReview;

@Repository
public class RentalReviewRepository {

    private final String GET_REVIEW_BY_ID_SQL = "select * from rental_reviews where id = ?";
    private final String GET_REVIEWS_BY_PROPERTY_ID_SQL = "select * from rental_reviews where property_id = ? order by created_date DESC";
    private final String GET_REVIEWS_BY_USER_ID_SQL = "select * from rental_reviews where user_id = ? order by created_date DESC";
    private final String CREATE_REVIEW_SQL = "insert into rental_reviews (id, user_id, property_id, title, monthly_rental_cost, floor, apartment_floor_area, rental_floor_area, furnishings, shared_toilet, rules, rental_start_date, rental_duration, occupants, rating, comments, status, images) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private final String GET_REVIEW_COUNT_BY_PROPERTY_ID_SQL = "select count(id) from rental_reviews where property_id = ?";
    private final String UPDATE_REVIEW_BY_REVIEW_ID_SQL = "update rental_reviews set title = ? , monthly_rental_cost = ?, floor = ?, apartment_floor_area = ?, rental_floor_area = ?, furnishings = ?, shared_toilet = ?, rules = ?, rental_start_date = ?, rental_duration = ?, occupants = ?, rating = ?, comments = ?, status = ? where id = ?";
    private final String GET_LATEST_REVIEWS_BY_BUILDING_NAME_SQL = "select * from rental_reviews join(select id as p_id, building from properties where building = ?) as table1 on table1.p_id=rental_reviews.property_id where property_id != ? order by created_date DESC limit ? offset ?";

    @Autowired
    private JdbcTemplate template;

    public Optional<RentalReview> getReviewById(String id) throws NotFoundException {
        BeanPropertyRowMapper<RentalReview> newInstance = BeanPropertyRowMapper.newInstance(RentalReview.class);
        newInstance.setPrimitivesDefaultedForNullValue(true);
        RentalReview response = template.queryForObject(
                GET_REVIEW_BY_ID_SQL,
                newInstance,
                id);
        if (response == null)
            throw new NotFoundException("review not found");
        return Optional.of(response);
    }

    public List<RentalReview> getReviewsByPropertyId(Integer propId) throws NotFoundException {
        BeanPropertyRowMapper<RentalReview> newInstance = BeanPropertyRowMapper.newInstance(RentalReview.class);
        newInstance.setPrimitivesDefaultedForNullValue(true);

        List<RentalReview> response = template.query(
                GET_REVIEWS_BY_PROPERTY_ID_SQL,
                newInstance,
                propId);

        if (response.isEmpty())
            throw new NotFoundException("no reviews found for propertyId: " + propId);
        return response;
    }

    public List<RentalReview> getReviewsByUserId(Integer userId) throws NotFoundException {
        BeanPropertyRowMapper<RentalReview> newInstance = BeanPropertyRowMapper.newInstance(RentalReview.class);
        newInstance.setPrimitivesDefaultedForNullValue(true);

        List<RentalReview> response = template.query(
                GET_REVIEWS_BY_USER_ID_SQL,
                newInstance,
                userId);

        if (response.isEmpty())
            throw new NotFoundException("no reviews found for userId: " + userId);
        return response;
    }

    public List<RentalReview> getReviewsByBuildingName(String building, Integer propertyIdExcluded, Integer limit, Integer offset) throws NotFoundException {
        BeanPropertyRowMapper<RentalReview> newInstance = BeanPropertyRowMapper.newInstance(RentalReview.class);
        newInstance.setPrimitivesDefaultedForNullValue(true);

        List<RentalReview> response = template.query(
                GET_LATEST_REVIEWS_BY_BUILDING_NAME_SQL,
                newInstance,
                building, propertyIdExcluded, limit, offset);

        if (response.isEmpty())
            throw new NotFoundException("no reviews found for building: " + building);
        return response;
    }

    public String saveReview(RentalReview r) throws UpdateException {
        KeyHolder generatedKey = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(CREATE_REVIEW_SQL, new String[] { "id" });
                ps.setString(1, r.getId());
                ps.setInt(2, r.getUserId());
                ps.setInt(3, r.getPropertyId());
                ps.setString(4, r.getTitle());
                ps.setString(5, r.getMonthlyRentalCost());
                ps.setString(6, r.getFloor());
                ps.setString(7, r.getApartmentFloorArea());
                ps.setString(8, r.getRentalFloorArea());
                ps.setString(9, r.getFurnishings());
                ps.setBoolean(10, r.getSharedToilet());
                ps.setString(11, r.getRules());
                ps.setDate(12, r.getRentalStartDate());
                ps.setString(13, r.getRentalDuration());
                ps.setInt(14, r.getOccupants());
                ps.setBigDecimal(15, r.getRating());
                ps.setString(16, r.getComments());
                ps.setString(17, r.getStatus());
                ps.setString(18, r.getImages());
                return ps;
            }
        };
        int rowsUpdated = template.update(psc, generatedKey);
        if (rowsUpdated <= 0)
            throw new UpdateException("review already exists");
        return r.getId();
    }

    public Integer getReviewCountByPropertyId(Integer propertyId) {
        return template.queryForObject(GET_REVIEW_COUNT_BY_PROPERTY_ID_SQL, Integer.class, propertyId);
    }

    public Integer updateReview(RentalReview r) {
        return template.update(UPDATE_REVIEW_BY_REVIEW_ID_SQL,
                r.getTitle(), r.getMonthlyRentalCost(), r.getFloor(), r.getApartmentFloorArea(), r.getRentalFloorArea(),
                r.getFurnishings(), r.getSharedToilet(), r.getRules(), r.getRentalStartDate(), r.getRentalDuration(),
                r.getOccupants(), r.getRating(), r.getComments(), r.getStatus(), r.getId());
    }
}
