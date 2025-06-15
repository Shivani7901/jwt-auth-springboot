package com.example.jwt_auth_springboot.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.example.jwt_auth_springboot.Token.RefreshToken;
import com.example.jwt_auth_springboot.Entity.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    @Query("SELECT rt FROM RefreshToken rt JOIN FETCH rt.user WHERE rt.token = :token")
    Optional<RefreshToken> findByToken(String token);

    List<RefreshToken> findByUser(User user);
}
// Compare this snippet from src/main/java/com/example/jwt_auth_springboot/Service/RefreshTokenService.java: