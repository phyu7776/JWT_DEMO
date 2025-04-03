package com.example.jwt.service.admin;

import java.util.List;
import java.util.Map;

public interface AdminService {
    Map<String, Object> approveUser(List<String> userIds);
}
