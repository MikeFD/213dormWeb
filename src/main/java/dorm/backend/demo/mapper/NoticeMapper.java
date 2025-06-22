package dorm.backend.demo.mapper;

import dorm.backend.demo.entity.Notice;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoticeMapper {

    @Insert("INSERT INTO announcements (" +
            "announcement_id, title, content, publish_time, importance_level, " +
            "publisher_id, created_at, updated_at, is_deleted" +
            ") VALUES (" +
            "#{announcementId}, #{title}, #{content}, #{publishTime}, #{importanceLevel}, " +
            "#{publisherId}, #{createdAt}, #{updatedAt}, #{isDeleted}" +
            ")")
    void insertNotice(Notice notice);

    @Select("SELECT " +
            "announcement_id AS announcementId, " +
            "title, " +
            "content, " +
            "publish_time AS publishTime, " +
            "importance_level AS importanceLevel, " +
            "publisher_id AS publisherId, " +
            "created_at AS createdAt, " +
            "updated_at AS updatedAt, " +
            "is_deleted AS isDeleted " +
            "FROM announcements ORDER BY publish_time DESC")
    List<Notice> findAllNotices();

    @Select("SELECT " +
            "announcement_id AS announcementId, " +
            "title, " +
            "content, " +
            "publish_time AS publishTime, " +
            "importance_level AS importanceLevel, " +
            "publisher_id AS publisherId, " +
            "created_at AS createdAt, " +
            "updated_at AS updatedAt, " +
            "is_deleted AS isDeleted " +
            "FROM announcements WHERE announcement_id = #{id}")
    Notice getNoticeById(String id); // 注意这里的参数类型应与数据库字段类型一致，改为 String
}