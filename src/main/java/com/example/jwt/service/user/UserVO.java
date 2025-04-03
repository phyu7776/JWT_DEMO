package com.example.jwt.service.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserVO {

    private String UID;

    private String userId;

    private String name;

    private String nickname;

    private String role;

    private LocalDate birthday;

    private String token;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Getter
    @RequiredArgsConstructor
    public enum role {
        USER("USER"),
        ADMIN("ADMIN");

        private final String role;
    }

    @Getter
    @RequiredArgsConstructor
    public enum STATE {
        DELETE("DELETE"),
        WAIT("WAIT"),
        USE("USE")
        ;

        private final String name;
    }
}
