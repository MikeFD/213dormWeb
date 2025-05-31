package dorm.backend.demo.utilsTest;

import dorm.backend.demo.utils.PasswordUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordTest {
    private String password = "1234567";
    private PasswordUtils passwordUtils;

    @BeforeEach
    public void setUp() {
        passwordUtils = new PasswordUtils(); // 手动创建实例
    }
    @Test
    public void testJiaMiAndCheck() {
        String passwordHash = passwordUtils.encrypt(password);

        assertNotNull(password);

        boolean flag = passwordUtils.checkPassword(password, passwordHash);
        assertTrue(flag);
    }

}
