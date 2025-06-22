package dorm.backend.demo.controller;

import dorm.backend.demo.common.ResultVO;
import dorm.backend.demo.dtos.NoticeDTO;
import dorm.backend.demo.entity.Notice;
import dorm.backend.demo.entity.NoticeRole;
import dorm.backend.demo.entity.User;
import dorm.backend.demo.entity.UserRole;
import dorm.backend.demo.mapper.NoticeMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/notice")
@Tag(name = "公告管理", description = "公告信息管理接口")
public class NoticeController {

    private static final Logger logger = LoggerFactory.getLogger(NoticeController.class);
    private final NoticeMapper noticeMapper;

    @Autowired
    public NoticeController(NoticeMapper noticeMapper) {
        this.noticeMapper = noticeMapper;
    }

    /**
     * 获取所有公告（无需权限）
     */
    @GetMapping("/list")
    @Operation(summary = "获取所有公告", description = "返回系统中所有的公告信息")
    public ResultVO getAllNotices() {
        try {
            List<Notice> notices = noticeMapper.findAllNotices();
            return ResultVO.success(notices);
        } catch (Exception e) {
            logger.error("获取公告列表失败", e);
            return ResultVO.error("服务器内部错误");
        }
    }

    /**
     * 根据ID获取公告详情（无需权限）
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取公告详情", description = "返回指定ID的公告详细信息")
    public ResultVO getNoticeById(
            @Parameter(name = "id", description = "公告ID", in = ParameterIn.PATH)
            @PathVariable String id) {
        try {
            Notice notice = noticeMapper.getNoticeById(id);
            if (notice == null) {
                return ResultVO.error("404", "未找到该公告");
            }
            return ResultVO.success(notice);
        } catch (Exception e) {
            logger.error("获取公告详情失败", e);
            return ResultVO.error("服务器内部错误");
        }
    }

    @PostMapping("/publish")
    @Operation(summary = "发布公告", description = "管理员发布公告")
    public ResultVO publishNotice(
            @RequestBody NoticeDTO noticeDTO,
            @AuthenticationPrincipal User user) {

        // 权限检查
        if (!user.getRole().equals(UserRole.ADMIN)) {
            return ResultVO.error("403", "无权限操作");
        }

        // 转换重要性级别
        NoticeRole importanceLevel;
        try {
            importanceLevel = NoticeRole.valueOf(noticeDTO.getImportanceLevel().toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResultVO.error("400", "无效的重要性级别");
        }

        try {
            // 验证用户ID
            if (user.getUserId() == null || user.getUserId().isEmpty()) {
                return ResultVO.error("400", "无效的用户ID");
            }

            // 使用UUID生成唯一公告ID
            String announcementId = java.util.UUID.randomUUID().toString();

            // 构建 Notice 对象
            Notice notice = new Notice();
            notice.setAnnouncementId(announcementId); // ✅ 必须设置
            notice.setTitle(noticeDTO.getTitle());
            notice.setContent(noticeDTO.getContent());
            notice.setImportanceLevel(importanceLevel);
            notice.setPublishTime(new Date());
            notice.setPublisherId(user.getUserId());
            notice.setCreatedAt(new Date());
            notice.setUpdatedAt(new Date());
            notice.setIsDeleted(false);

            // 插入数据库
            noticeMapper.insertNotice(notice);

            return ResultVO.success("公告发布成功");
        } catch (Exception e) {
            logger.error("公告发布失败", e);
            return ResultVO.error("500", "公告发布失败: " + e.getMessage());
        }
    }

    // 验证重要性级别（可选保留）
    private boolean isValidImportanceLevel(String level) {
        return "普通".equals(level) || "紧急".equals(level) || "重要".equals(level) ||
                "NORMAL".equals(level) || "URGENT".equals(level) || "IMPORTANT".equals(level);
    }

}