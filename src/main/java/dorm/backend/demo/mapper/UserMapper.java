package dorm.backend.demo.mapper;

import dorm.backend.demo.entity.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    @Select("SELECT user_id, username, password_hash, user_role, avatar_url FROM users WHERE user_id = #{id}")
    User getUserById(String id);

    @Select("SELECT user_id, username, password_hash, user_role, avatar_url FROM users WHERE user_id = #{id}")
    User getUserByUsername(String username);
}
