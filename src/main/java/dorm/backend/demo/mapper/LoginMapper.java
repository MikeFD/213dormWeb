package dorm.backend.demo.mapper;

import dorm.backend.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginMapper {

    @Select("SELECT * FROM users WHERE username = #{username} AND password_hash = #{password}")
    User findUserByCredentials(
            @Param("username") String username,
            @Param("password") String password
    );
}