package com.example.jwt.service.user;

public interface UserService {

    UserVO login(UserVO user);

    void signup(UserVO user);
}