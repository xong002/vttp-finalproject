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

import project.vttpproject.model.user.User;
import project.vttpproject.model.user.UserInfo;

@Repository
public class UserRepository {

    private final String GET_USER_DETAILS_BY_EMAIL_SQL = "select * from user_details where email = ?";
    private final String GET_USER_BY_ID_SQL = "select * from user where id = ?";
    private final String GET_USER_DETAILS_BY_ID_SQL = "select * from user_details where id = ?";
    private final String CREATE_USERDETAILS_SQL = "insert into user_details (email, password, role, status) values (?, ?, ?, ?)";
    private final String CREATE_USER_SQL = "insert into user (user_details_id, display_name) values (?,?)";
    private final String UPDATE_USER_BY_ID_SQL = "update user_details set email = ?, password = ?, role = ?, status = ? where id = ?";
    private final String UPDATE_DISPLAYNAME_BY_ID_SQL = "update user set display_name = ? where id = ?";

    @Autowired
    private JdbcTemplate template;

    public Optional<UserInfo> getUserInfoByEmail(String email){
        UserInfo response = template.queryForObject(
                GET_USER_DETAILS_BY_EMAIL_SQL,
                BeanPropertyRowMapper.newInstance(UserInfo.class),
                email);
        if (response == null)
            return Optional.empty();
        return Optional.of(response);
    }

    public Optional<User> getUserById(Integer id) {
        User response = template.queryForObject(
                GET_USER_BY_ID_SQL,
                BeanPropertyRowMapper.newInstance(User.class),
                id);
        if (response == null)
            return Optional.empty();
        return Optional.of(response);
    }

    public Optional<UserInfo> getUserInfoById(Integer id) {
        UserInfo response = template.queryForObject(
                GET_USER_DETAILS_BY_ID_SQL,
                BeanPropertyRowMapper.newInstance(UserInfo.class),
                id);
        if (response == null)
            return Optional.empty();
        return Optional.of(response);
    }

    public Optional<Integer> saveUserInfo(UserInfo userDetails) {
        KeyHolder generatedKey = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(CREATE_USERDETAILS_SQL, new String[] { "id" });
                ps.setString(1, userDetails.getEmail());
                ps.setString(2, userDetails.getPassword());
                ps.setString(3, userDetails.getRole().toString());
                ps.setString(4, userDetails.getStatus());
                return ps;
            }
        };
        int rowsUpdated = template.update(psc, generatedKey);
        if (rowsUpdated <= 0)
            return Optional.empty();
        return Optional.of(generatedKey.getKey().intValue());
    }

    public Optional<Integer> saveUser(User user) {
        KeyHolder generatedKey = new GeneratedKeyHolder();
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(CREATE_USER_SQL, new String[] { "id" });
                ps.setInt(1, user.getUserDetailsId());
                ps.setString(2, user.getDisplayName());
                return ps;
            }
        };
        int rowsUpdated = template.update(psc, generatedKey);
        if (rowsUpdated <= 0)
            return Optional.empty();
        return Optional.of(generatedKey.getKey().intValue());
    }

    public Integer updateUserInfo(UserInfo userDetails) {
        Integer rowsUpdated = template.update(UPDATE_USER_BY_ID_SQL, userDetails.getEmail(), userDetails.getPassword(),
                userDetails.getRole(), userDetails.getStatus(), userDetails.getId());
        return rowsUpdated;
    }

    public Integer updateUserDisplayName(String displayName, Integer userId){
        Integer rowsUpdated = template.update(UPDATE_DISPLAYNAME_BY_ID_SQL, displayName, userId);
        return rowsUpdated;
    }

}
