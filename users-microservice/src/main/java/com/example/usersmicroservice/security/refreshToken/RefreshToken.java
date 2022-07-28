package com.example.usersmicroservice.security.refreshToken;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Instant;

@Entity(name = "refresh_tokens")
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RefreshToken {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String token;
    @JsonIgnore
    private String username;
    @CreationTimestamp
    private Instant createdAt;
}
