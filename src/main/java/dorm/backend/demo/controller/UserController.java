package dorm.backend.demo.controller;

import dorm.backend.demo.common.ResultVO;
import dorm.backend.demo.entity.User;
import dorm.backend.demo.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/user")
@Tag(name = "用户管理", description = "用户信息修改接口")
public class UserController {

    @Resource
    private UserMapper userMapper;

    @Operation(summary = "更新用户名", description = "修改当前登录用户的用户名")
    @PutMapping("/update-username")
    public ResultVO updateUsername(
            @RequestBody Map<String, String> request,
            @AuthenticationPrincipal User user) {

        String newUsername = request.get("newUsername");
        userMapper.updateUsername(user.getUserId(), newUsername);
        user.setUsername(newUsername); // 更新当前用户对象的用户名
        return ResultVO.success("用户名更新成功");
    }
}