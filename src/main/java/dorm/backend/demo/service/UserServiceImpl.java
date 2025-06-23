package dorm.backend.demo.service;

import dorm.backend.demo.dtos.RegisterDTO;
import dorm.backend.demo.entity.User;
import dorm.backend.demo.entity.UserRole;
import dorm.backend.demo.exception.BusinessException;
import dorm.backend.demo.mapper.RegisterMapper;
import dorm.backend.demo.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final RegisterMapper registerMapper;
    @Autowired
    private PasswordUtils passwordUtils;

    @Override
    @Transactional
    public User register(RegisterDTO dto) {
        // 基本空值检查（推荐添加）
        if (dto.getUsername() == null || dto.getUsername().trim().isEmpty()) {
            throw new BusinessException("用户名不能为空");
        }
        if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
            throw new BusinessException("密码不能为空");
        }

        // 原有验证保持不变
        if (registerMapper.existsByUsername(dto.getUsername())) {
            throw new BusinessException("用户名已存在");
        }
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new BusinessException("两次密码不一致");
        }

        // 构建用户实体（保持不变）
        User user = new User();
        user.setUserId(java.util.UUID.randomUUID().toString());
        user.setUsername(dto.getUsername());
        user.setRole(UserRole.USER);
        user.setAvatar("https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png");
        user.setPassword(passwordUtils.encrypt(dto.getPassword()));

        registerMapper.insertUser(user);
        return user;
    }
}