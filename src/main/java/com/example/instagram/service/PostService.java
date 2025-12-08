package com.example.instagram.service;

import com.example.instagram.dto.request.PostCreateRequest;
import com.example.instagram.dto.response.PostResponse;
import com.example.instagram.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface PostService {
    PostResponse create(PostCreateRequest postCreateRequest, MultipartFile image, Long userId);
    Post findById(Long postId);
    PostResponse getPost(Long postId);
    List<PostResponse> getAllPosts();
    List<PostResponse> getPostsByUsername(String username);
    long countByUserId(Long userId);
    List<PostResponse> getAllPostWithStats();

    // 피드 조회
    Slice<PostResponse> getFeedPosts(Long userId, Pageable pageable);

    // 전체 조회
    Slice<PostResponse> getAllPostsPaging(Pageable pageable);

}
