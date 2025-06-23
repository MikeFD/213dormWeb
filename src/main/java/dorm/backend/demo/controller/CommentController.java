package dorm.backend.demo.controller;

import dorm.backend.demo.dtos.CommentRequestDTO;
import dorm.backend.demo.dtos.CommentResponseDTO;
import dorm.backend.demo.service.CommentService;
import dorm.backend.demo.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<?> submitComment(
            @RequestBody CommentRequestDTO request,
            HttpServletRequest httpRequest) {

        // 原有的手动验证逻辑保持不变
        if (request.getContent() == null || request.getContent().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("评论内容不能为空");
        }

        if (request.getContent().length() > 500) {
            return ResponseEntity.badRequest().body("评论内容不能超过500字符");
        }

        try {
            CommentResponseDTO response = commentService.addComment(request, httpRequest);
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
}