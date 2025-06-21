package dorm.backend.demo.service;

import dorm.backend.demo.dtos.RegisterDTO;
import dorm.backend.demo.entity.User;

public interface UserService {
    User register(RegisterDTO dto);
}