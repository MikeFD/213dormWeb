package dorm.backend.demo.mapper;

import dorm.backend.demo.entity.Comment;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CommentMapper {
    @Insert("INSERT INTO comments (content, comment_by, created_at) " +
            "VALUES (#{content}, #{commentBy}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "commentId", keyColumn = "comment_id")
    void insertComment(Comment comment);
}