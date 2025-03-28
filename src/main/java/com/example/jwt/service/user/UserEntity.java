package com.example.jwt.service.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor
public class UserEntity {

    @Id
    private String userId;

    private String password;

    @Builder
    public UserEntity(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
