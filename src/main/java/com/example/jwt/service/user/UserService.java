package com.example.jwt.service.user;

import java.util.List;
import java.util.Map;

public interface UserService {

    UserVO login(UserVO user);

    void signup(UserVO user);

    List<UserVO> getAllUsers();

    UserVO userReissue(UserVO user);
}