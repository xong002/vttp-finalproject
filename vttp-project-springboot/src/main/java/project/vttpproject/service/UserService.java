package project.vttpproject.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.vttpproject.model.User;
import project.vttpproject.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepo;

    public Optional<User> getUserById(Integer id) {
        return userRepo.getUserById(id);
    } 

}
