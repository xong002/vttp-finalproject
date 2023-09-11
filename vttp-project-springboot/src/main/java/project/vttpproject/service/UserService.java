package project.vttpproject.service;

import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import project.vttpproject.config.JwtService;
import project.vttpproject.exception.UpdateException;
import project.vttpproject.model.user.AuthenticationRequest;
import project.vttpproject.model.user.AuthenticationResponse;
import project.vttpproject.model.user.User;
import project.vttpproject.model.user.UserInfo;
import project.vttpproject.model.user.UserDetailsInput;
import project.vttpproject.model.user.UserSummary;
import project.vttpproject.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepo;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;



    @Transactional(rollbackFor = { UpdateException.class, SQLException.class })
    public AuthenticationResponse saveNewUser(UserDetailsInput input) throws UpdateException {

        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(input.getEmail());
        userInfo.setPassword(passwordEncoder.encode(input.getPassword()));
        userInfo.setRole(input.getRole());
        userInfo.setStatus(input.getStatus());

        Optional<Integer> optUserDetails = userRepo.saveUserInfo(userInfo);
        if (optUserDetails.isEmpty())
            throw new UpdateException("user details already exists");

        User newUser = new User();
        newUser.setUserDetailsId(optUserDetails.get());
        newUser.setDisplayName(input.getDisplayName());

        Optional<Integer> optUser = userRepo.saveUser(newUser);
        if (optUser.isEmpty())
            throw new UpdateException("display name already exists");

        String jwtToken = jwtService.generateToken(userInfo);
        return AuthenticationResponse.builder().token(jwtToken).build();

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        UserInfo userInfo = userRepo.getUserInfoByEmail(request.getEmail()).orElseThrow();
        
        String jwtToken = jwtService.generateToken(userInfo);
        return AuthenticationResponse.builder().token(jwtToken).build();

    }

    public Optional<UserSummary> getUserById(Integer id) {
        Optional<User> optUserById = userRepo.getUserById(id);
        if (optUserById.isEmpty())
            return Optional.empty();
        Optional<UserInfo> optUserDetailsById = userRepo.getUserInfoById(optUserById.get().getUserDetailsId());
        if (optUserDetailsById.isEmpty())
            return Optional.empty();
        return Optional.of(new UserSummary(optUserById.get(), optUserDetailsById.get()));
    }

    public Integer updateUserInfo(UserInfo input, Integer userId) throws UpdateException {
        Optional<User> optUserById = userRepo.getUserById(userId);
        if (optUserById.isEmpty())
            throw new UpdateException("user already exists");
        input.setId(optUserById.get().getUserDetailsId());
        return userRepo.updateUserInfo(input);
    }

    // update display name separately
    public Integer updateUserDisplayName(String displayName, Integer userId){
        return userRepo.updateUserDisplayName(displayName, userId);
    }

    //TODO: try authenticate service 1.53.50
}
