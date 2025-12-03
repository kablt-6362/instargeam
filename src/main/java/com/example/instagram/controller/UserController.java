package com.example.instagram.controller;

import com.example.instagram.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    @GetMapping("/{username}")
    public String profile(@PathVariable String username,
                          Model model,
                          @AuthenticationPrincipal CustomUserDetails userDetails
                          ){

        return "user/profile";
    }

}
