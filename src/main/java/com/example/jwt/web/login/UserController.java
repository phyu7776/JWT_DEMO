package com.example.jwt.web.login;

import com.example.jwt.config.excetion.APIException;
import com.example.jwt.config.redis.LettuceUtil;
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
    private final LettuceUtil lettuceUtil;

    @PostMapping("/login")
    public ResponseEntity<UserVO> login(@RequestBody UserVO user) {

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


    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody UserVO user) {
        try {
            lettuceUtil.deleteTokens(user.getToken());
        } catch (APIException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reissue")
    public ResponseEntity<UserVO> reissue(@RequestBody UserVO user) {
        return ResponseEntity.ok(userService.userReissue(user));
    }
}
