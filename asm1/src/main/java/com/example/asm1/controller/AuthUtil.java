package com.example.asm1.controller;

import jakarta.servlet.http.HttpSession;
import com.example.asm1.Entity.User;

public class AuthUtil {

    public static boolean isAdmin(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        return user != null && user.getRoleId() == 1;
    }
}
