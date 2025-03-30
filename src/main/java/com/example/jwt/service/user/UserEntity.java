package com.example.jwt.service.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor
public class UserEntity {

    @Id
    private String userId;

    private String password;

    private String role;

    private boolean approved;

    private Timestamp createdAt;

    private Timestamp approveDate;

    @Builder
    public UserEntity(String userId, String password, String role) {
        this.userId = userId;
        this.password = password;
        this.role = role;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = Timestamp.valueOf(LocalDateTime.now());
    }


    public void approve() {
        this.approved = true;
        this.approveDate = new Timestamp(System.currentTimeMillis());
    }

}
