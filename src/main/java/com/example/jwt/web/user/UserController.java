package com.example.jwt.web.user;

import com.example.jwt.config.excetion.APIException;
import com.example.jwt.utils.LettuceUtil;
import com.example.jwt.service.user.UserService;
import com.example.jwt.service.user.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/getUsers")
    public ResponseEntity<Map<String, Object>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PatchMapping("/update")
    public ResponseEntity<UserVO> updateUser(@RequestBody UserVO users) {
        return ResponseEntity.ok(userService.updateUser(users));
    }

    @PatchMapping("/changePassword")
    public ResponseEntity<Void> changePassword(@RequestBody Map<String, String> passwordInfo) {
        userService.changePassword(passwordInfo);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUser(@RequestBody List<UserVO> users) {
        userService.deleteUser(users);
        return ResponseEntity.noContent().build();
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
