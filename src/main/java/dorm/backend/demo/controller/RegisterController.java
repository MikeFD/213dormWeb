package dorm.backend.demo.controller;

import dorm.backend.demo.common.ResultVO;
import dorm.backend.demo.dtos.RegisterDTO;
import dorm.backend.demo.entity.User;
import dorm.backend.demo.service.UserService;
import dorm.backend.demo.vos.RegisterVO;
import dorm.backend.demo.exception.BusinessException;
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
        try {
            User user = userService.register(dto);
            return ResultVO.successWithGeneric(new RegisterVO(user.getUserId(), user.getUsername()));
        } catch (BusinessException e) {
            // 处理业务异常
            return ResultVO.error(e.getMessage());
        } catch (Exception e) {
            // 处理其他异常
            return ResultVO.error("注册失败: " + e.getMessage());
        }
    }
}