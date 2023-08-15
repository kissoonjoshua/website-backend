package com.kissoonjoshua.website.users.service;

import com.kissoonjoshua.website.users.dto.TokenResponse;
import com.kissoonjoshua.website.users.model.User;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IUserService {
    User saveUser(User user);
    List<User> fetchUsers();
    User fetchUserById(String userId);
    void deleteUserById(String userId);
    Mono<TokenResponse> retrieveTokens(String code);
    Mono<TokenResponse> useRefreshToken(String refreshToken);
    void checkIfUserExists(TokenResponse tokenResponse);
}
