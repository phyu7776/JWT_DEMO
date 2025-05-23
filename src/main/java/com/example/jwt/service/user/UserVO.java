package com.example.jwt.service.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserVO {

    private String UID;

    private String userId;

    private String name;

    private String nickname;

    private String role;

    private String state;

    private LocalDate birthday;

    private Map<String, String> token;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    public static UserVO toUserVO(UserEntity entity) {
        return UserVO.builder()
                .UID(entity.getUID())
                .userId(entity.getUserId())
                .name(entity.getName())
                .nickname(entity.getNickname())
                .state(entity.getState())
                .role(entity.getRole())
                .birthday(entity.getBirthDate())
                .build();
    }

    @Getter
    @RequiredArgsConstructor
    public enum ROLE {
        USER("USER"), // 일반
        SUPERVISOR("SUPERVISOR"), //일반관리
        ADMIN("ADMIN");

        private final String role;
    }

    @Getter
    @RequiredArgsConstructor
    public enum STATE {
        DELETE("D"),
        WAIT("W"),
        USE("U")
        ;

        private final String name;
    }
}
