package dorm.backend.demo.dtos;

import lombok.Data;

@Data
public class RegisterDTO {
    private String username;
    private String password;
    private String confirmPassword;
}