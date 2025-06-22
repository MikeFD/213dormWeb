package dorm.backend.demo.mapper;

import dorm.backend.demo.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface RegisterMapper {
    @Select("SELECT COUNT(*) FROM users WHERE username = #{username}")
    boolean existsByUsername(String username);

    @Insert("INSERT INTO users (user_id, username, password_hash, user_role, avatar_url) " +
            "VALUES (#{userId}, #{username}, #{passwordHash}, #{userRole}, #{avatarUrl})")
    int insertUser(User user);

}