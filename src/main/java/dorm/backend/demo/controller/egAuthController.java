package dorm.backend.demo.controller;

import dorm.backend.demo.common.ResultVO;
import dorm.backend.demo.dtos.EgTextDTO;
import dorm.backend.demo.entity.User;
import dorm.backend.demo.mapper.EgAuthMapper;
import dorm.backend.demo.mapper.EgMapper;
import dorm.backend.demo.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/auth")
@Tag(name = "带权示例接口", description = "关于带权接口的示例操作，会包含get, post的基本方法，还有jwt的示例，让jwt的作用显化")
public class egAuthController {
    @Resource
    private EgAuthMapper egAuthMapper;

    @GetMapping("/hello")
    public ResultVO hello(
            @RequestParam(value = "name", defaultValue = "World") String name, @AuthenticationPrincipal User user) {

        List<GrantedAuthority> authentication = user.getAuthorities();
        List<String> authorities = authentication.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        // 返回用户名＋对应的账号权限
        String res = String.format("Hello %s %s, u r a %s account!!!, ur userId is %s, ur avatar is %s", name, user.getUsername(), String.join(", ", authorities), user.getUserId(),user.getAvatar()); // ✅ 从 Token 中提取的用户名);

        return ResultVO.success(res);
    }

    @PostMapping("/publish")
    public ResultVO publish(@RequestBody String text, @AuthenticationPrincipal User user) {
        String username = user.getUsername();
        egAuthMapper.insertText(text, username);

        String res = String.format("%s 创建文本 %s", username, text);
        return ResultVO.success(res);
    }

    @PutMapping("/publish")
    public ResultVO update(@RequestBody EgTextDTO egTextDTO, @AuthenticationPrincipal User user) {
        String text = egTextDTO.getText();
        int id = egTextDTO.getId();

        String username = user.getUsername();
        String oldText = egAuthMapper.getText(id);

        egAuthMapper.updateText(text, username, id);

        String res = String.format("%s 将文本 %s 修改为 %s", username, oldText, text);

        return ResultVO.success(res);
    }
}
