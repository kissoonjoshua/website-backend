package com.kissoonjoshua.website.users.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;

@Configuration
public class CognitoClient {
	@Bean
	public CognitoIdentityProviderClient initCognitoClient() {
		CognitoIdentityProviderClient identityProviderClient = CognitoIdentityProviderClient.builder()
				.region(Region.US_EAST_1)
	            .credentialsProvider(ProfileCredentialsProvider.create("websitedev"))
	            .build();
		
		return identityProviderClient;
	}
}
