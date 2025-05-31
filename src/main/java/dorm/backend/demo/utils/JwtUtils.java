package dorm.backend.demo.utils;

import dorm.backend.demo.entity.User;
import dorm.backend.demo.entity.UserRole;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class JwtUtils {

    private static String secretKey;
    public static long ttlMillis;//设置为7天过期
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.secret}")
    private String configSecret;

    @Value("${jwt.exp_time}")
    private long configTtlMillis;

    @PostConstruct
    public void init() {
//        logger.info("Config Secret Key: {}", configSecret); // 添加日志
        secretKey = configSecret;
        ttlMillis = configTtlMillis;
    }

    public static String createJwt(User user) {
//        Map<String, Object> claims = new HashMap<>();
        try{
            String username = user.getUsername();
            UserRole userRole = UserRole.ADMIN;
            String avatar = user.getAvatar();
            String userId = user.getUserId();

            List<String> authorities = Collections.singletonList(userRole.name());

            long expMillis = System.currentTimeMillis() + ttlMillis;
            Date exp = new Date(expMillis);

            //生成 HMAC 密钥，根据提供的字节数组长度选择适当的 HMAC 算法，并返回相应的 SecretKey 对象。
            SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

            return Jwts.builder()
                    .subject(username) // 设置主体（用户ID）
                    .claim("userId", userId)
                    .claim("roles", authorities)//为了应付过滤器搞的
                    .claim("role", userRole.name())//权限提取的位置
                    .claim("avatar", avatar)
                    .setIssuedAt(new Date()) // 设置签发时间
                    .setExpiration(exp) // 设置过期时间
                    .signWith(key)
                    .compact();
        } catch (JwtException e) {
            logger.error(e.getMessage());
            return null;
        }

    }

    public static Claims parseJWT(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException e) {
            // 可选：记录日志、抛出自定义异常等
            logger.error("诶，就是解析不出来，那咋啦: {}", e.getMessage(), e);
            throw new JwtException(e.getMessage());
        }
    }
}