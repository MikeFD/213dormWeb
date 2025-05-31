package dorm.backend.demo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface EgAuthMapper {
    @Insert("INSERT INTO eg_auth(样例, create_by) VALUES(#{text}, #{username})")
    void insertText(String text, String username);

    @Update("UPDATE eg_auth SET 样例 = #{newText}, update_by = #{username} WHERE id = #{id}")
    void updateText(String newText, String username, int id);

    @Select("SELECT 样例 FROM eg_auth WHERE id = #{id}")
    String getText(int id);
}
