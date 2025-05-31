package dorm.backend.demo.utilsTest;
import static org.junit.jupiter.api.Assertions.*;


import dorm.backend.demo.DormBackendApplication;
import dorm.backend.demo.entity.User;
import dorm.backend.demo.entity.UserRole;
import dorm.backend.demo.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest // 加载整个 Spring 上下文
public class JwtTest {
    private JwtUtils jwtUtils;
    private String username = "jyk";
    private UserRole userRole = UserRole.ADMIN;
    private User user;
//    private String token;

    @BeforeEach
    public void setUp() {
        user = new User(); // 初始化 user
        user.setRole(userRole);
        user.setUsername(username);
        String uuid = UUID.randomUUID().toString();;
        user.setUserId(uuid);
    }

    /**
     * 功能：测试jwt是否创建成功
     */
    @Test
    public void testGenerateAndParseJwt() {
        String token = JwtUtils.createJwt(user);
        assertNotNull(token);

        Claims claims = JwtUtils.parseJWT(token);

        System.out.println(token);

        // 断言解析后的 subject 是预期值
        assertNotNull(claims);
        assertEquals(username, claims.getSubject());
    }
}
