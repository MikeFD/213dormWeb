package dorm.backend.demo.utils;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

/**
 * 功能：加密工具
 * 作者：GreenT
 * @method encrypt checkPassword
 */
public class PasswordUtils {
    private Argon2PasswordEncoder encoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    /**
     * 功能：对密码进行加密
     * 作者: GreenT
     * @param rawPassword
     * @return
     */
    public String encrypt(String rawPassword) {
        String password = encoder.encode(rawPassword);

        return password;
    }

    /**
     * 功能：检测密码是否正确
     * 作者：GreenT
     * @param rawPassword 用户给到的原生密码
     * @param encodedPassword 加密过后的密码，由数据库中提取而出
     * @return
     */

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
