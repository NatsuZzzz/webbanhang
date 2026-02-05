package com.example.asm1.controller;

import com.example.asm1.Entity.User;
import com.example.asm1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/management")
    public String management(Model model) {
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("user", new User());
        return "admin/users";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute User user) {
        if (user.getId() != null) {
            // Dùng Integer id giúp hết lỗi gạch đỏ ở findById
            User oldUser = userRepository.findById(user.getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (user.getPassword() == null || user.getPassword().isBlank()) {
                user.setPassword(oldUser.getPassword());
            }
        } else {
            if (user.getPassword() == null || user.getPassword().isBlank()) {
                throw new RuntimeException("Password không được để trống");
            }
        }
        userRepository.save(user);
        return "redirect:/admin/user/management";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) { // Đổi sang Integer
        model.addAttribute("user", userRepository.findById(id).orElseThrow());
        model.addAttribute("users", userRepository.findAll());
        return "admin/users";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) { // Đổi sang Integer
        userRepository.deleteById(id);
        return "redirect:/admin/user/management";
    }
}