package dorm.backend.demo.controller;

import dorm.backend.demo.common.ResultVO;
import dorm.backend.demo.dtos.CommentRequestDTO;
import dorm.backend.demo.dtos.CommentResponseDTO;
import dorm.backend.demo.entity.Comment;
import dorm.backend.demo.entity.User;
import dorm.backend.demo.mapper.CommentMapper;
import dorm.backend.demo.service.CommentService;
import dorm.backend.demo.exception.BusinessException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "评论", description = "")
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @PostMapping
    public ResponseEntity<?> submitComment(
            @RequestBody CommentRequestDTO request,
            @AuthenticationPrincipal User user) {

        // 原有的手动验证逻辑保持不变
        if (request.getContent() == null || request.getContent().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("评论内容不能为空");
        }

        if (request.getContent().length() > 500) {
            return ResponseEntity.badRequest().body("评论内容不能超过500字符");
        }

        try {
            CommentResponseDTO response = commentService.addComment(request, user);
            return ResponseEntity.ok(response);
        } catch (BusinessException e) {
            // 处理业务异常
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            // 处理其他运行时异常
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (Exception e) {
            // 处理其他所有异常
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("系统错误");
        }
    }

    @GetMapping
    public ResultVO getAllComment() {
        List<String> comment = commentMapper.getAllComment();

        return ResultVO.success(comment);
    }
}