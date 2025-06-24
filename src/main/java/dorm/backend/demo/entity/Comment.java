package dorm.backend.demo.entity;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
//    private Long commentId;    // 主键ID
    private String content;    // 评论内容
    private String commentBy;  // 评论人ID
//    private LocalDateTime createdAt; // 创建时间
}