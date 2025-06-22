package dorm.backend.demo.entity;

import io.jsonwebtoken.Claims;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class User {
    private String userId;
    private String username;
    private String password;
    private UserRole role;
    private List<GrantedAuthority> authorities;
    private String avatar = "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png";

    // 新增字段用于注册功能
    private String passwordHash;     // 用于存储加密后的密码
    private String userRole;         // 用于数据库存储的角色字符串
    private String avatarUrl;        // 用于数据库存储的头像URL
    private LocalDateTime createdAt; // 创建时间

    public User() {

    }

    // 基于jwt传入的方法
    public User(String username, List<GrantedAuthority> authorities, Claims claims) {
        this.username = username;
        this.authorities = authorities;
        String role1 = (String)claims.get("role");
        this.avatar = (String)claims.get("avatar");
        this.role = UserRole.valueOf(role1);
        this.userId = (String)claims.get("userId");
    }

    public User(String userId, String username, String password, UserRole role,
                List<GrantedAuthority> authorities, String avatar,
                String passwordHash, String userRole, String avatarUrl,
                LocalDateTime createdAt) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.authorities = authorities;
        this.avatar = avatar;
        this.passwordHash = passwordHash;
        this.userRole = userRole;
        this.avatarUrl = avatarUrl;
        this.createdAt = createdAt;
    }
}