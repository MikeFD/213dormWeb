package dorm.backend.demo.mapper;

import dorm.backend.demo.entity.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Insert("INSERT INTO comment (content, comment_by) " +
            "VALUES (#{content}, #{commentBy})")
//    @Options(useGeneratedKeys = true, keyProperty = "commentId", keyColumn = "comment_id")
    void insertComment(Comment comment);

    @Select("SELECT content from comment")
    List<String> getAllComment();
}