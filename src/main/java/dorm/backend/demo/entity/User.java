package dorm.backend.demo.entity;

import io.jsonwebtoken.Claims;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
public class User {
    private String userId;
    private String username;
    private String password;
    private UserRole role;
    private List<GrantedAuthority> authorities;
    private String avatar = "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png";

    public User() {

    }
    //基于jwt传入的方法
    public User(String username, List<GrantedAuthority> authorities, Claims claims) {
        this.username = username;
        this.authorities = authorities;
        String role1 = (String)claims.get("role");
        this.avatar = (String)claims.get("avatar");
        this.role = UserRole.valueOf(role1);
        this.userId = (String)claims.get("userId");
    }
}
