package com.example.jwt.service.user;

import java.util.List;
import java.util.Map;

public interface UserService {

    UserVO login(UserVO user);

    void signup(UserVO user);

    Map<String, Object> getAllUsers();

    UserVO userReissue(UserVO user);

    void deleteUser(List<UserVO> user);

    UserVO updateUser(UserVO user);

    void changePassword(Map<String, String> passwordInfo);
}