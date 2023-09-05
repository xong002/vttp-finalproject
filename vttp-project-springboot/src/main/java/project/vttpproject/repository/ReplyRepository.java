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

import project.vttpproject.exception.UpdateException;
import project.vttpproject.model.replies.Reply;

@Repository
public class ReplyRepository {
    
    private final String GET_REPLY_BY_ID_SQL = "select * from replies where id = ?";
    private final String GET_REPLIES_BY_REVIEW_ID_SQL = "select * from replies where rental_reviews_id = ?";
    private final String GET_REPLIES_BY_USER_ID_SQL = "select * from replies where user_id = ?";
    private final String CREATE_REPLY_SQL = "insert into replies (id, user_id, rental_reviews_id, comments, status) values (?,?,?,?,?)";

    @Autowired
    private JdbcTemplate template;

    
    public Optional<Reply> getReplyById(String id) throws UpdateException {
        BeanPropertyRowMapper<Reply> newInstance = BeanPropertyRowMapper.newInstance(Reply.class);
        newInstance.setPrimitivesDefaultedForNullValue(true);
        Reply response = template.queryForObject(
                GET_REPLY_BY_ID_SQL,
                newInstance,
                id);
        if (response == null)
            throw new UpdateException("reply not found");
        return Optional.of(response);
    }

    public List<Reply> getRepliesByReviewId(String reviewId) throws UpdateException {
        BeanPropertyRowMapper<Reply> newInstance = BeanPropertyRowMapper.newInstance(Reply.class);
        newInstance.setPrimitivesDefaultedForNullValue(true);
        List<Reply> response = template.query(
                GET_REPLIES_BY_REVIEW_ID_SQL,
                newInstance,
                reviewId);
        if (response.isEmpty())
            throw new UpdateException("no replies found for reviewId: " + reviewId);
        return response;
    }

    public List<Reply> getRepliesByUserId(Integer userId) throws UpdateException {
        BeanPropertyRowMapper<Reply> newInstance = BeanPropertyRowMapper.newInstance(Reply.class);
        newInstance.setPrimitivesDefaultedForNullValue(true);
        List<Reply> response = template.query(
                GET_REPLIES_BY_USER_ID_SQL,
                newInstance,
                userId);
        if (response.isEmpty())
            throw new UpdateException("no replies found for userId: " + userId);
        return response;
    }

    public String saveReply(Reply r) throws UpdateException {
        KeyHolder generatedKey = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(CREATE_REPLY_SQL, new String[] { "id" });
                ps.setString(1, r.getId());
                ps.setInt(2, r.getUserId());
                ps.setString(3, r.getRentalReviewsId());
                ps.setString(4, r.getComments());
                ps.setString(5, r.getStatus());
                return ps;
            }
        };
        int rowsUpdated = template.update(psc, generatedKey);
        if (rowsUpdated <= 0)
            throw new UpdateException("reply already exists");
        return r.getId();
    }
}
