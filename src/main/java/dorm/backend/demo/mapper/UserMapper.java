package dorm.backend.demo.mapper;

import dorm.backend.demo.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("SELECT user_id, username, password_hash, user_role, avatar_url FROM users WHERE user_id = #{id}")
    User getUserById(String id);

    @Select("SELECT user_id, username, password_hash, user_role, avatar_url FROM users WHERE username = #{username}")
    User getUserByUsername(String username);

    @Update("UPDATE users SET username = #{newUsername} WHERE user_id = #{userId}")
    void updateUsername(@Param("userId") String userId, @Param("newUsername") String newUsername);

    @Update("UPDATE users SET avatar_url = #{newAvatar} WHERE user_id = #{userId}")
    void updateAvatar(@Param("userId") String userId, @Param("newAvatar") String newAvatar);
}