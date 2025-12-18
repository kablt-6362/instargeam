package com.example.instagram.service;

import com.example.instagram.dto.request.ProfileUpdateRequest;
import com.example.instagram.dto.request.SignUpRequest;
import com.example.instagram.dto.response.ProfileResponse;
import com.example.instagram.dto.response.UserResponse;
import com.example.instagram.entity.Role;
import com.example.instagram.entity.User;
import com.example.instagram.exception.BusinessException;
import com.example.instagram.exception.ErrorCode;
import com.example.instagram.repository.FollowRepository;
import com.example.instagram.repository.PostRepository;
import com.example.instagram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FollowRepository followRepository;
    private final PostRepository postRepository;
    private final FileService fileService;


    /**
     * 사용자를 등록합니다.
     * @param signUpRequest 등록할 사용자의 정보
     * @return 등록된 사용자
     */
    @Override
    @Transactional
    public User register(SignUpRequest signUpRequest){
        User user = User.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .name(signUpRequest.getName())
                .role(Role.USER)
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .build();
        return userRepository.save(user);
    }

    /**
     * 사용자 이름이 존재하는지 확인합니다.
     * @param username 확인할 사용자 이름
     * @return 존재하면 true, 그렇지 않으면 false
     */
    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * ID로 사용자를 찾습니다.
     * @param userId 찾을 사용자의 ID
     * @return 찾은 사용자
     * @throws BusinessException 사용자를 찾을 수 없는 경우
     */
    @Override
    public User findById(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    /**
     * 사용자 프로필을 가져옵니다.
     * @param username 프로필을 가져올 사용자의 이름
     * @return 프로필 정보
     */
    @Override
    public ProfileResponse getProfile(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        long postsCount = postRepository.countByUserId(user.getId());
        long followerCount = followRepository.countByFollowingId(user.getId());
        long followingCount = followRepository.countByFollowerId(user.getId());


        return ProfileResponse.from(user,postsCount,followerCount,followingCount);

    }

    /**
     * 사용자 이름으로 사용자를 찾습니다.
     * @param username 찾을 사용자의 이름
     * @return 찾은 사용자
     */
    @Override
    public User findByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow();
    }

    /**
     * ID로 사용자를 가져옵니다.
     * @param userId 가져올 사용자의 ID
     * @return 사용자 정보
     */
    @Override
    public UserResponse getUserById(Long userId){
        User user = findById(userId);
        return UserResponse.from(user);
    }

    /**
     * 프로필을 업데이트합니다.
     * @param userId 업데이트할 사용자의 ID
     * @param profileUpdateRequest 업데이트할 프로필 정보
     * @param profileImg 업데이트할 프로필 이미지
     */
    @Override
    @Transactional
    public void updateProfile(Long userId, ProfileUpdateRequest profileUpdateRequest, MultipartFile profileImg){
        User user = findById(userId);

        // 프로필 이미지 처리
        if(profileImg != null && !profileImg.isEmpty()){
            String savedFilename = fileService.saveFile(profileImg);
            String imageUrl = "/uploads/" + savedFilename;
            user.updateProfileImage(imageUrl);
        }

        user.updateProfile(profileUpdateRequest.getName(), profileUpdateRequest.getBio());

    }

    /**
     * 키워드로 사용자를 검색합니다.
     * @param keyword 검색할 키워드
     * @return 검색된 사용자 목록
     */
    @Override
    public List<UserResponse> searchUsers(String keyword){
        return userRepository.searchByKeyword(keyword).stream()
                .map(UserResponse::from)
                .toList()
                ;
    }

}
