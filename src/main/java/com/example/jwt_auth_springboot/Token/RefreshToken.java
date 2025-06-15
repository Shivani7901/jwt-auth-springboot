package com.example.jwt_auth_springboot.Token;

import com.example.jwt_auth_springboot.Entity.User;
import com.example.jwt_auth_springboot.Enums.RefreshTokenStatus;
import jakarta.persistence.*;
import java.util.Date;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @ManyToOne(fetch = FetchType.EAGER) // Changed to EAGER
    private User user;

    @Enumerated(EnumType.STRING)
    private RefreshTokenStatus status;

    private Date createdAt;
    private Date expiresAt;

    public void setStatus(RefreshTokenStatus status) {
        this.status = status;
    }
}