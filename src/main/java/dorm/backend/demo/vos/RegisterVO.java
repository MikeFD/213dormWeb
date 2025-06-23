package dorm.backend.demo.vos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterVO {
    private String userId;
    private String username;
}