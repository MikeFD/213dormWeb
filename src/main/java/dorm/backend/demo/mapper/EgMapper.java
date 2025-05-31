package dorm.backend.demo.mapper;

import dorm.backend.demo.dtos.EgTextDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface EgMapper {
    @Insert("INSERT INTO eg(样例) VALUES(#{text})")
    void insertText(String text);

    @Update("UPDATE eg SET 样例 = #{newText} WHERE id = #{id}")
    void updateText(String newText, int id);

    @Select("SELECT 样例 FROM eg WHERE id = #{id}")
    String getText(int id);
}
