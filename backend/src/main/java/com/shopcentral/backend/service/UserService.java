package com.shopcentral.backend.service;

import com.shopcentral.backend.model.User;
import com.shopcentral.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Register User
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    // Login User
    public Optional<User> loginUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent() &&
                user.get().getPassword().equals(password)) {
            return user;
        }

        return Optional.empty();
    }
}