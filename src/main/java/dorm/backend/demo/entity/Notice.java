package dorm.backend.demo.entity;

import lombok.Data;
import java.util.Date;

@Data
public class Notice {
    private String announcementId; // 修改为 String 类型，对应数据库字段 announcement_id
    private String title;           // 公告标题
    private String content;         // 公告内容
    private Date publishTime;       // 发布时间 (publish_time)
    private NoticeRole importanceLevel; // 重要程度，使用枚举类型
    private String publisherId;     // 发布者ID (publisher_id)
    private Date createdAt;         // 创建时间 (created_at)
    private Date updatedAt;         // 更新时间 (updated_at)
    private Boolean isDeleted;      // 是否删除 (is_deleted)

    public Notice() {
    }

    public Notice(String title, String content, Date publishTime, NoticeRole importanceLevel, String publisherId) {
        this.title = title;
        this.content = content;
        this.publishTime = publishTime;
        this.importanceLevel = importanceLevel;
        this.publisherId = publisherId;
    }

    public Notice(String announcementId, String title, String content, Date publishTime,
                  NoticeRole importanceLevel, String publisherId, Date createdAt, Date updatedAt, Boolean isDeleted) {
        this.announcementId = announcementId;
        this.title = title;
        this.content = content;
        this.publishTime = publishTime;
        this.importanceLevel = importanceLevel;
        this.publisherId = publisherId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isDeleted = isDeleted;
    }
}