package com.example.jwt.service.admin;

import com.example.jwt.service.user.UserVO;

import java.util.List;
import java.util.Map;

public interface AdminService {
    Map<String, Object> approveUser(List<UserVO> users);

}
