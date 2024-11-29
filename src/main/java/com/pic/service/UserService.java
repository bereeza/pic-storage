package com.pic.service;

import com.pic.dto.user.UserInfoDto;
import com.pic.entity.User;
import com.pic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    public UserInfoDto getCurrentUser() {
        String email = getSecurityContextHolderUser().getAttribute("email");
        if (email == null) {
            throw new IllegalArgumentException("User is not authorized.");
        }

        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            String username = email.split("@")[0] + UUID.randomUUID();
            User newUser = User.builder()
                    .email(email)
                    .username(username)
                    .build();

            userRepository.save(newUser);
            return buildUserInfo(newUser);
        }

        return buildUserInfo(user.get());
    }

    @Override
    @SneakyThrows
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        log.info("{}", super.loadUser(userRequest));
        return super.loadUser(userRequest);
    }

    private UserInfoDto buildUserInfo(User user) {
        return UserInfoDto.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }

    private OAuth2User getSecurityContextHolderUser() {
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();

        return (OAuth2User) auth.getPrincipal();
    }
}
