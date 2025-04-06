package com.example.jwt.service.user;

import com.example.jwt.config.base.BaseEntity;
import com.example.jwt.config.constant.EntitiyConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(
        name = "Users",
        indexes = {
                @Index(name = "idx_user_search", columnList =  "createdAt, role, state, name, nickname")
        }
)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseEntity {

    private String name;

    private String nickname;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private String userId;

    private String password;

    private String role;

    private String state;

    private boolean approved;

    private LocalDateTime approveDate;

    public void approve() {
        this.state = UserVO.STATE.USE.getName();
        this.approved = true;
        this.approveDate = LocalDateTime.now();
    }

    public void changeRole(String role) {
        if (!ObjectUtils.isEmpty(role)) {
            this.role = role;
        } else {
            this.role = UserVO.role.USER.getRole();
        }
    }

    @Override
    public String getEntityType() {
        return EntitiyConstant.USER_ENTITY.getValue();
    }
}
