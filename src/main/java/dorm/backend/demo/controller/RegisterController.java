package dorm.backend.demo.controller;

import dorm.backend.demo.common.ResultVO;
import dorm.backend.demo.dtos.RegisterDTO;
import dorm.backend.demo.entity.User;
import dorm.backend.demo.service.UserService;
import dorm.backend.demo.vos.RegisterVO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/register")
public class RegisterController {
    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResultVO register(@RequestBody RegisterDTO dto) {
        // 验证逻辑在服务层处理
        User user = userService.register(dto);
        return ResultVO.successWithGeneric(new RegisterVO(user.getUserId(), user.getUsername()));
    }
}