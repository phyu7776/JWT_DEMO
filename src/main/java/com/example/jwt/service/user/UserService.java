package com.example.jwt.service.user;

import java.util.List;

public interface UserService {

    UserVO login(UserVO user);

    void signup(UserVO user);

    List<UserVO> getAllUsers();
}