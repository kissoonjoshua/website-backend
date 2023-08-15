package com.kissoonjoshua.website.users.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kissoonjoshua.website.users.dto.TokenResponse;
import com.kissoonjoshua.website.users.model.User;
import com.kissoonjoshua.website.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@Value
@Slf4j
@AllArgsConstructor
public class UserService implements IUserService {
    final UserRepository userRepository;
    final WebClient webClient;
    final CognitoIdentityProviderClient cognitoClient;
    String clientId = "6enolsl1c0maa9acv6jutdtk5";
    String clientSecret = "d0u5ac8kqtje7qehdbph0vigv4gkknlbjpqto83c2m71tqvccmh";
    String redirectUri = "http://localhost:3000";
    String userPoolId = "us-east-1_NLfZOTMqM";

    @Override
    public void checkIfUserExists(TokenResponse tokenResponse) {
        String[] chunks = tokenResponse.getId_token().split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
//            String header = new String(decoder.decode(chunks[0]));
        String payload = new String(decoder.decode(chunks[1]));
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(payload);
        JsonElement sub = jsonObject.get("sub");
        JsonElement email = jsonObject.get("email");
        JsonElement username = jsonObject.get("cognito:username");
        if(!userRepository.existsById(sub.getAsString())) {
            User newUser = User.builder()
                    .id(sub.getAsString())
                    .username(username.getAsString())
                    .email(email.getAsString())
                    .events(new ArrayList<String>())
                    .build();
            userRepository.save(newUser);
        }
    }

    @Override
    public Mono<TokenResponse> retrieveTokens(String code) {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "authorization_code");
        requestBody.add("client_id", clientId);
        requestBody.add("code", code);
        requestBody.add("redirect_uri", redirectUri);

        String credentials = clientId + ":" + clientSecret;
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

        return webClient.post()
            .uri("https://websitedev.auth.us-east-1.amazoncognito.com/oauth2/token")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .header(HttpHeaders.AUTHORIZATION, "Basic " + encodedCredentials)
            .body(BodyInserters.fromFormData(requestBody))
            .retrieve()
            .bodyToMono(TokenResponse.class);
    }

    @Override
    public Mono<TokenResponse> useRefreshToken(String refreshToken) {
            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("grant_type", "refresh_token");
            requestBody.add("client_id", clientId);
            requestBody.add("refresh_token", refreshToken);
            requestBody.add("redirect_uri", redirectUri);

            String credentials = clientId + ":" + clientSecret;
            String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

            return webClient.post()
                    .uri("https://websitedev.auth.us-east-1.amazoncognito.com/oauth2/token")
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    .header(HttpHeaders.AUTHORIZATION, "Basic " + encodedCredentials)
                    .body(BodyInserters.fromFormData(requestBody))
                    .retrieve()
                    .bodyToMono(TokenResponse.class);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }
    @Override
    public List<User> fetchUsers() {
        return userRepository.findAll();
    }
    @Override
    public User fetchUserById(String userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.orElse(null);
    }
    @Override
    public void deleteUserById(String userId) {
        userRepository.deleteById(userId);
    }
}
