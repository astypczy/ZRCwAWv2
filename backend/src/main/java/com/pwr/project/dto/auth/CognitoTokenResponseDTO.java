package com.pwr.project.dto.auth;
import com.fasterxml.jackson.annotation.JsonProperty;


public record CognitoTokenResponseDTO(
    @JsonProperty("access_token") String accessToken,
    @JsonProperty("id_token") String idToken,
    @JsonProperty("refresh_token") String refreshToken,
    @JsonProperty("expires_in") Integer expiresIn,
    @JsonProperty("token_type") String tokenType
) {}
