package com.kissoonjoshua.website.users.controller;

import com.kissoonjoshua.website.users.apiresponse.APIResponse;
import com.kissoonjoshua.website.users.dto.*;
import com.kissoonjoshua.website.users.model.User;
import com.kissoonjoshua.website.users.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Base64;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api/users")
@Slf4j
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/exchangecode")
    public Mono<ResponseEntity<APIResponse>> exchangeCode(@RequestBody Code code) {
        log.info("in");
        Mono<TokenResponse> response = userService.retrieveTokens(code.getCode());

        return response.flatMap(tokenResponse -> {
            APIResponse apiResponse = APIResponse.builder()
                    .message("Code exchanged successfully")
                    .isSuccessful(true)
                    .statusCode(HttpStatus.OK.value())
                    .data(tokenResponse)
                    .build();
            log.info("Token response: {}", tokenResponse);
            userService.checkIfUserExists(tokenResponse);
            return Mono.just(ResponseEntity.ok(apiResponse));
        }).onErrorResume(error -> {
            log.error("Error occurred while retrieving tokens", error);
            APIResponse apiResponse = APIResponse.builder()
                    .message("Error occurred while retrieving tokens")
                    .isSuccessful(false)
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse));
        });
    }

    @PostMapping("/userefreshtoken")
    public Mono<ResponseEntity<APIResponse>> useRefreshToken(@RequestBody RefreshToken refreshToken) {
        log.info("in refresh");
        Mono<TokenResponse> response = userService.useRefreshToken(refreshToken.getRefreshToken());

        return response.flatMap(tokenResponse -> {
            APIResponse apiResponse = APIResponse.builder()
                    .message("Code exchanged successfully")
                    .isSuccessful(true)
                    .statusCode(HttpStatus.OK.value())
                    .data(tokenResponse)
                    .build();
            log.info("Token response: {}", tokenResponse);
            return Mono.just(ResponseEntity.ok(apiResponse));
        }).onErrorResume(error -> {
            log.error("Error occurred while retrieving tokens", error);
            APIResponse apiResponse = APIResponse.builder()
                    .message("Error occurred while retrieving tokens")
                    .isSuccessful(false)
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse));
        });
    }

    @GetMapping("")
    public List<User> fetchUsers() {
        return userService.fetchUsers();
    }
    @GetMapping("/{id}")
    public User fetchUserById(@PathVariable("id") String id) {
        return userService.fetchUserById(id);
    }

    @PostMapping("")
    public User saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @DeleteMapping("/{id}")
    public String deleteUserById(@PathVariable("id") String id) {
        userService.deleteUserById(id);
        return "User deleted";
    }
}
