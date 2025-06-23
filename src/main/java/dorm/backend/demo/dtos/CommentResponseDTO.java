package dorm.backend.demo.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentResponseDTO {
    private Long commentId;
    private String content;
    private String commentBy;
    private String createTime;
}