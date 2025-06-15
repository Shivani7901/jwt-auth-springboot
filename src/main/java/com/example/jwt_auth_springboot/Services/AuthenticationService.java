package com.example.jwt_auth_springboot.Services;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.Hibernate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.jwt_auth_springboot.Configuration.JwtService;
import com.example.jwt_auth_springboot.Entity.User;
import com.example.jwt_auth_springboot.Enums.RefreshTokenStatus;
import com.example.jwt_auth_springboot.Repository.RefreshTokenRepository;
import com.example.jwt_auth_springboot.Token.RefreshToken;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public String authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        var user = userService.getUserByUsername(username);
        return jwtService.generateToken(user);
    }

    @Transactional
    public RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setStatus(RefreshTokenStatus.ACTIVE);
        refreshToken.setCreatedAt(new Date());
        refreshToken.setExpiresAt(new Date(System.currentTimeMillis() + jwtService.getRefreshTokenExpiration()));
        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    @Transactional
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiresAt().toInstant().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token expired!");
        }
        return token;
    }

    @Transactional
    public void revokeTokens(User user) {
        List<RefreshToken> refreshTokens = refreshTokenRepository.findByUser(user);
        for (RefreshToken refreshToken : refreshTokens) {
            refreshToken.setStatus(RefreshTokenStatus.REVOKED);
            refreshTokenRepository.save(refreshToken);
        }
    }

    @Transactional
   public RefreshToken getByToken(String token) {
    RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow();
    Hibernate.initialize(refreshToken.getUser()); // Explicitly initialize the User
    return refreshToken;
}
    public String generateToken(User user) {
        return jwtService.generateToken(user);
    }
}