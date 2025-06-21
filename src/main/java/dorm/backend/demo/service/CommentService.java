package dorm.backend.demo.service;

import dorm.backend.demo.dtos.CommentRequestDTO;
import dorm.backend.demo.dtos.CommentResponseDTO;
import dorm.backend.demo.entity.Comment;
import dorm.backend.demo.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentMapper commentMapper;

    @Transactional
    public CommentResponseDTO addComment(CommentRequestDTO request, HttpServletRequest httpRequest) {
        // 从请求属性中获取用户信息
        String userId = (String) httpRequest.getAttribute("userId");

        if (userId == null) {
            throw new RuntimeException("未找到用户信息");
        }

        // 服务层再次验证
        if (request.getContent() == null || request.getContent().trim().isEmpty()) {
            throw new RuntimeException("评论内容不能为空");
        }

        if (request.getContent().length() > 500) {
            throw new RuntimeException("评论内容不能超过500字符");
        }

        Comment comment = Comment.builder()
                .content(request.getContent())
                .commentBy(userId)
                .createdAt(LocalDateTime.now())
                .build();

        // 持久化到数据库
        commentMapper.insertComment(comment);

        // 构建响应DTO
        return CommentResponseDTO.builder()
                .commentId(comment.getCommentId())
                .content(comment.getContent())
                .commentBy(comment.getCommentBy())
                .createTime(comment.getCreatedAt()
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build();
    }
}