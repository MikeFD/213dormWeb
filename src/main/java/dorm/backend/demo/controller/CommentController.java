package dorm.backend.demo.controller;

import dorm.backend.demo.dtos.CommentRequestDTO;
import dorm.backend.demo.dtos.CommentResponseDTO;
import dorm.backend.demo.service.CommentService;
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

        // 手动验证请求数据
        if (request.getContent() == null || request.getContent().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("评论内容不能为空");
        }

        if (request.getContent().length() > 500) {
            return ResponseEntity.badRequest().body("评论内容不能超过500字符");
        }

        try {
            CommentResponseDTO response = commentService.addComment(request, httpRequest);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}