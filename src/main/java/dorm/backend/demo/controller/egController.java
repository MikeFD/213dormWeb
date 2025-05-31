package dorm.backend.demo.controller;

import dorm.backend.demo.common.ResultVO;
import dorm.backend.demo.dtos.EgTextDTO;
import dorm.backend.demo.entity.User;
import dorm.backend.demo.mapper.EgMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "示例接口", description = "关于普通接口的示例操作，会包含get, post, put的基本方法")
public class egController {
    @Resource
    private EgMapper egMapper;

    @Operation(
            summary = "GET 方法示例：返回问候语",
            description = "输入一个字符串参数 name，返回 'Hello [name]!'。若未提供 name，默认为 'World'",
            responses = {
                    @ApiResponse(responseCode = "200", description = "成功返回问候语",
                            content = @Content(schema = @Schema(implementation = String.class)))
            }
    )
    @GetMapping("/hello")
    public String hello(
            @Parameter(description = "要问候的名称")
            @RequestParam(value = "name", defaultValue = "World") String name) {


        return String.format("Hello %s!", name);
    }

    @PostMapping("/publish")
    public ResultVO publish(@RequestBody String text) {
        egMapper.insertText(text);

        return ResultVO.success(text);
    }

    @PutMapping("/publish")
    public ResultVO update(@RequestBody EgTextDTO egTextDTO) {
        String text = egTextDTO.getText();
        int id = egTextDTO.getId();

        String oldText = egMapper.getText(id);

        egMapper.updateText(text, id);

        String res = String.format("将文本 %s 修改为 %s", oldText, text);

        return ResultVO.success(res);
    }
}
