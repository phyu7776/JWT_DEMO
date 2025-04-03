package com.example.jwt.web.login;

import com.example.jwt.config.excetion.APIException;
import com.example.jwt.service.user.UserService;
import com.example.jwt.service.user.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserVO user) {

        if (ObjectUtils.isEmpty(user.getUserId())) {
            throw new APIException(APIException.ErrorCode.USER_INFO_INVALID);
        }

        return ResponseEntity.ok(userService.login(user));
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody UserVO user) {
        userService.signup(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
