package com.example.jwt.service.user;

public interface UserService {

    String login(UserVO user);

    void signup(UserVO user);
}