package dorm.backend.demo.dtos;

import lombok.Data;

@Data
public class NoticeDTO {
    private String title;           // 公告标题
    private String content;         // 公告内容
    private String importanceLevel; // 重要程度：普通/紧急/重要 或 NORMAL/URGENT/IMPORTANT
}