package project.vttpproject.service;

import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.vttpproject.exception.UpdateException;
import project.vttpproject.model.User;
import project.vttpproject.model.UserDetails;
import project.vttpproject.model.UserDetailsInput;
import project.vttpproject.model.UserSummary;
import project.vttpproject.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public Optional<UserSummary> getUserById(Integer id) {
        Optional<User> optUserById = userRepo.getUserById(id);
        if (optUserById.isEmpty())
            return Optional.empty();
        Optional<UserDetails> optUserDetailsById = userRepo.getUserDetailsById(optUserById.get().getUserDetailsId());
        if (optUserDetailsById.isEmpty())
            return Optional.empty();
        return Optional.of(new UserSummary(optUserById.get(), optUserDetailsById.get()));
    }

    @Transactional(rollbackFor = { UpdateException.class, SQLException.class })
    public Integer saveNewUser(UserDetailsInput input) throws UpdateException {

        UserDetails userDetails = new UserDetails();
        userDetails.setEmail(input.getEmail());
        userDetails.setPassword(input.getPassword());
        userDetails.setRole(input.getRole());
        userDetails.setStatus(input.getStatus());

        Optional<Integer> optUserDetails = userRepo.saveUserDetails(userDetails);
        if (optUserDetails.isEmpty())
            throw new UpdateException("duplicate email");

        User newUser = new User();
        newUser.setUserDetailsId(optUserDetails.get());
        newUser.setDisplayName(input.getDisplayName());

        Optional<Integer> optUser = userRepo.saveUser(newUser);
        if (optUser.isEmpty())
            throw new UpdateException("duplicate display name");

        return optUser.get();

    }

    public Integer updateUserDetails(UserDetails input, Integer userId) throws UpdateException {
        Optional<User> optUserById = userRepo.getUserById(userId);
        if (optUserById.isEmpty())
            throw new UpdateException("user id not found");
        input.setId(optUserById.get().getUserDetailsId());
        return userRepo.updateUserDetails(input);
    }

    // update display name separately
}
