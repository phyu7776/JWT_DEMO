package com.example.jwt.service.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
public class UserVO {

    private String userId;
    private String password;

    public enum role {
        USER,
        ADMIN;

        public String getRole() {
            return this.name();
        }
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
