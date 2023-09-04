package project.vttpproject.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import project.vttpproject.model.User;
import project.vttpproject.model.UserDetails;

@Repository
public class UserRepository {

    private final String GET_USER_BY_ID_SQL = "select * from user where id = ";
    private final String CREATE_USERDETAILS_SQL = "insert into user_details (email, password, role, status) values (?, ?, ?, ?)";
    private final String CREATE_USER_SQL = "insert into user (user_details_id, display_name) values (?,?)";
    

    @Autowired
    private JdbcTemplate template;

    public Optional<User> getUserById(Integer id) {
            User response = template.queryForObject(
                            GET_USER_BY_ID_SQL,
                            BeanPropertyRowMapper.newInstance(User.class),
                            id);
            if (response == null) return Optional.empty();
            return Optional.of(response);
    }

    public Optional<Integer> saveUserDetails(UserDetails userDetails) {
        KeyHolder generatedKey = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(CREATE_USERDETAILS_SQL, new String[]{"id"});
                ps.setString(1, userDetails.getEmail());
                ps.setString(2, userDetails.getPassword());
                ps.setString(3, userDetails.getRole());
                ps.setString(4,userDetails.getStatus());
                return ps;
            }
        };
            int rowsUpdated = template.update(psc, generatedKey);
            if (rowsUpdated <= 0) return Optional.empty();
            return Optional.of(generatedKey.getKey().intValue());
    }


    public Optional<Integer> saveUser(User user) {
        KeyHolder generatedKey = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(CREATE_USER_SQL, new String[]{"id"});
                ps.setInt(1, user.getUserDetailsId());
                ps.setString(2, user.getDisplayName());
                return ps;
            }
        };
        int rowsUpdated = template.update(psc, generatedKey);
        if (rowsUpdated <= 0) return Optional.empty();
        return Optional.of(generatedKey.getKey().intValue());
    }

}
