package com.nuevospachile.gestion_tareas_api.service;

import com.nuevospachile.gestion_tareas_api.entity.User;
import com.nuevospachile.gestion_tareas_api.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User validateCredentials(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(u -> u.getPassword().equals(password))
                .orElse(null);
    }
}
