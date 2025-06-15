package dorm.backend.demo.vos;

import lombok.Data;

@Data
public class LoginVO {
    private String username;
    private String userId;
    private String token;

}