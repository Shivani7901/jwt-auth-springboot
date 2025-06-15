package com.example.jwt_auth_springboot.Dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;
    private String refreshToken;
}
// Compare this snippet from src/main/java/com/example/jwt_auth_springboot/Service/AuthService.java: