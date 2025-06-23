package dorm.backend.demo.controller;

import dorm.backend.demo.common.ResultVO;
import dorm.backend.demo.dtos.LoginDTO;
import dorm.backend.demo.utils.PasswordUtils;
import dorm.backend.demo.vos.LoginVO;
import dorm.backend.demo.entity.User;
import dorm.backend.demo.mapper.LoginMapper;
import dorm.backend.demo.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    private PasswordUtils passwordUtils;

    @PostMapping
    public ResultVO login(@RequestBody LoginDTO loginDTO) {
        User user = loginMapper.findUserByCredentials(
                loginDTO.getUsername()
        );
        logger.error(user.getPassword());


        if (user != null) {
            if (passwordUtils.checkPassword(loginDTO.getPassword(), user.getPassword())) {
                String token = JwtUtils.createJwt(user);
                if (token != null) {
                    // 创建LoginVO对象并设置值
                    LoginVO loginVO = new LoginVO();
                    loginVO.setUsername(user.getUsername());
                    loginVO.setUserId(user.getUserId());  // 假设User实体有id字段
                    loginVO.setToken(token);

                    // 返回包含LoginVO的成功响应
                    return ResultVO.success(loginVO);
                }
                return ResultVO.error("500", "Token generation failed");
            }
            return ResultVO.error("500", "账号或者密码错误");
        }
        return ResultVO.error("401", "Invalid credentials");
    }
}