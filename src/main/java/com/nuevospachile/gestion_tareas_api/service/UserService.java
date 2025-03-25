package com.nuevospachile.gestion_tareas_api.service;

import com.nuevospachile.gestion_tareas_api.entity.User;
import com.nuevospachile.gestion_tareas_api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public User updateUser(Long id, User data) {
        User existing = findUserById(id);
        existing.setUsername(data.getUsername());
        existing.setPassword(data.getPassword());
        return userRepository.save(existing);
    }

    public void deleteUser(Long id) {
        User existing = findUserById(id);
        userRepository.delete(existing);
    }
}
