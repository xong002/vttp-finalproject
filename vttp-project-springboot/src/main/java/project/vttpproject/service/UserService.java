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
import project.vttpproject.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public Optional<User> getUserById(Integer id) {
        return userRepo.getUserById(id);
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
        if(optUser.isEmpty()) throw new UpdateException("duplicate display name");

        return optUser.get();

    }

}
