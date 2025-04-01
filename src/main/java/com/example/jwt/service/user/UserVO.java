package com.example.jwt.service.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public enum state {
        DELETE,
        WAIT,
        USE;

        public String getState() {
            return this.name();
        }
    }
}
