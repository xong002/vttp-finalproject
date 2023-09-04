package project.vttpproject.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import project.vttpproject.model.User;
import project.vttpproject.model.UserDetails;

@Repository
public class UserRepository {

    private final String GET_USER_BY_ID_SQL = "select * from user where id = ";
    private final String CREATE_USERDETAILS_SQL = "insert into user_details (email, password, role, status) values (?, ?, ?, ?)";

    @Autowired
    private JdbcTemplate template;

    public Optional<User> getUserById(Integer id) {
        // try {
            User response = template.queryForObject(
                            GET_USER_BY_ID_SQL,
                            BeanPropertyRowMapper.newInstance(User.class),
                            id);
            if (response == null) return Optional.empty();
            return Optional.of(response);
        // } catch (DataAccessException e) {
        //     throw new DatabaseException();
        // }
    }

    public Integer saveUserDetails(UserDetails userDetails) {
        // try {
            int rowsUpdated = template.update(
                    CREATE_USERDETAILS_SQL,
                    userDetails.getEmail(),
                    userDetails.getPassword(),
                    userDetails.getRole(),
                    userDetails.getStatus());
            if (rowsUpdated <= 0) return 0;
            return rowsUpdated;
        // } catch (DataAccessException e) {
        //     throw new DatabaseException();
        // }
    }

    public void saveUser() {
    }

}
