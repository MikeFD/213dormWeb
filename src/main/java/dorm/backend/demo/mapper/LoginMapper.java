package dorm.backend.demo.mapper;

import dorm.backend.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginMapper {

    @Select("SELECT user_id as userId, username, password_hash as password, user_role as userRole, avatar_url as avatar FROM users WHERE username = #{username}")
    User findUserByCredentials(
            @Param("username") String username
    );
}