package com.example.jwt.service.user;

import com.example.jwt.utils.UIDGenerator;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(
        name = "Users",
        indexes = {
                @Index(name = "idx_user_search", columnList =  "role, state, name, nickname")
        }
)
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String UID;

    private String name;

    private String nickname;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private String userId;

    private String password;

    private String role;

    private String state;

    private boolean approved;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime approveDate;

    @PrePersist
    public void prePersist() {
        this.UID = UIDGenerator.generateUID();
        if (ObjectUtils.isEmpty(this.state)) {
            this.state = UserVO.STATE.WAIT.getName();
        }
    }

    public void approve() {
        this.state = UserVO.STATE.USE.getName();
        this.approved = true;
        this.approveDate = LocalDateTime.now();
    }

}
