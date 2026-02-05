package com.example.asm1.service;

import com.example.asm1.Entity.User;
import com.example.asm1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // Đổi tất cả Long id thành Integer id
    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}