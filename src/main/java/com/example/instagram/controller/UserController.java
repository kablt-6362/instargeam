package com.example.instagram.controller;

import com.example.instagram.dto.response.PostResponse;
import com.example.instagram.dto.response.ProfileResponse;
import com.example.instagram.security.CustomUserDetails;
import com.example.instagram.service.FollowService;
import com.example.instagram.service.PostService;
import com.example.instagram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PostService postService;
    private final FollowService followService;

    @GetMapping("/{username}")
    public String profile(@PathVariable String username,
                          Model model,
                          @AuthenticationPrincipal CustomUserDetails userDetails
                          ){

        ProfileResponse profile = userService.getProfile(username);
        List<PostResponse> posts = postService.getPostsByUsername(username);
        model.addAttribute("profile",profile);
        model.addAttribute("posts",posts);

        boolean isFollowing = followService.isFollowing(userDetails.getId(),profile.getId());
        model.addAttribute("isFollowing",isFollowing);

        boolean isOwner = userDetails.getUsername().equals(username);
        model.addAttribute("isOwner",isOwner);

        return "user/profile";
    }

    // 팔로우 기능
    @PostMapping("/{username}/follow")
    public String toggleFollow(@PathVariable String username,
                               @AuthenticationPrincipal CustomUserDetails userDetails){
        // 새로운 서비스 구현(로그인한 사용자본인 , 팔로우 하려는 대상)
        followService.toggleFollow(userDetails.getId(),username);

        return "redirect:/users/"+username;
    }



}
