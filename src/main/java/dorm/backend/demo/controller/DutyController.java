// DutyController.java
package dorm.backend.demo.controller;

import dorm.backend.demo.common.ResultVO;
import dorm.backend.demo.entity.Duty;
import dorm.backend.demo.entity.User;
import dorm.backend.demo.mapper.DutyMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/duty")
@Tag(name = "值日管理", description = "值日表信息管理接口")
public class DutyController {

    @Resource
    private DutyMapper dutyMapper;

    @Operation(summary = "更新值日信息", description = "根据星期几更新值日表信息 (1-7表示周一到周日)")
    @PutMapping("/update")
    public ResultVO updateDuty(@RequestBody Duty duty, @AuthenticationPrincipal User user) {
        // 获取当前用户名
        String username = user.getUsername();

        // 验证星期范围 (1-7)
        if (duty.getDate() < 1 || duty.getDate() > 7) {
            return ResultVO.error(400, "日期范围必须在1-7之间");
        }

        // 检查该星期记录是否存在
        Duty existingDuty = dutyMapper.getDutyByDate(duty.getDate());

        if (existingDuty != null) {
            // 更新现有记录（传入username）
            dutyMapper.updateDuty(duty, username);
            return ResultVO.success("值日信息更新成功");
        } else {
            // 插入新记录（传入username）
            dutyMapper.insertDuty(duty, username);
            return ResultVO.success("值日信息创建成功");
        }
    }

    @Operation(summary = "获取值日信息", description = "根据星期几获取值日表信息 (1-7表示周一到周日)")
    @GetMapping("/get")
    public ResultVO getDutyByDate(@RequestParam Integer date, @AuthenticationPrincipal User user) {
        // 验证星期范围 (1-7)
        if (date < 1 || date > 7) {
            return ResultVO.error(400, "日期范围必须在1-7之间");
        }

        Duty duty = dutyMapper.getDutyByDate(date);
        if (duty != null) {
            return ResultVO.success(duty);
        } else {
            return ResultVO.error(404, "未找到该日期的值日信息");
        }
    }
    @Operation(summary = "获取值日信息", description = "根据星期几获取值日表信息 (1-7表示周一到周日)")
    @GetMapping("/get-all-duty")
    public ResultVO getDutyByDate(@AuthenticationPrincipal User user) {
        List<Duty> res = dutyMapper.getAllDuty();

        return ResultVO.success(res);
    }
}