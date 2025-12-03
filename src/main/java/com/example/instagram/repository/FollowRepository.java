package com.example.instagram.repository;

import com.example.instagram.entity.Follow;
import com.example.instagram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow,Long> {

    Optional<Follow> findByFollowerIdAndFollowingId(Long followerId,Long followingId);

    boolean existsByFollowerIdAndFollowingId(Long followerId, Long followingId);

    List<Follow> follower(User follower);

    long countByFollowerId(Long followerId);

    long countByFollowingId(Long followingId);

}
