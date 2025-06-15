package com.example.jwt_auth_springboot.AuthController;


import com.example.jwt_auth_springboot.Dto.AuthenticationRequest;
import com.example.jwt_auth_springboot.Dto.AuthenticationResponse;
import com.example.jwt_auth_springboot.Dto.TokenRefreshRequest;
import com.example.jwt_auth_springboot.Dto.TokenRefreshResponse;
import com.example.jwt_auth_springboot.Services.AuthenticationService;
import com.example.jwt_auth_springboot.Services.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationService authService;

    @PostMapping("/register")
    public String register(@RequestBody AuthenticationRequest request) {
        userService.saveUser(request.getUsername(), request.getPassword());
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest request) {
        var token = authService.authenticate(request.getUsername(), request.getPassword());
        var user = userService.getUserByUsername(request.getUsername());
        var refreshToken = authService.createRefreshToken(user);
        return new AuthenticationResponse(token, refreshToken.getToken());
    }

   @PostMapping("/refresh-token")
public TokenRefreshResponse refreshToken(@RequestBody TokenRefreshRequest request) {
    var refreshToken = authService.getByToken(request.getRefreshToken());
    authService.verifyExpiration(refreshToken);

    var user = refreshToken.getUser();
    var newAccessToken = authService.generateToken(user);

    return new TokenRefreshResponse(newAccessToken);
}
    @DeleteMapping("/revoke/{username}")
    public String revokeTokens(@PathVariable String username) {
        var user = userService.getUserByUsername(username);
        authService.revokeTokens(user);
        return "Refresh tokens revoked!";
    }
}