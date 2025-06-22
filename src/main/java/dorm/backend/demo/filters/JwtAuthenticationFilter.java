package dorm.backend.demo.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import dorm.backend.demo.common.ResultVO;
import dorm.backend.demo.entity.User;
import dorm.backend.demo.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@ServletComponentScan
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String HEADER_STRING = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 从请求头中获取 token
        String token = getTokenFromRequest(request);

        if (token != null) {

            // 解析 token 中的信息
            try{
                Claims claims = JwtUtils.parseJWT(token);
                String username = claims.getSubject();
                List<String> rolesString = claims.get("roles", List.class);

                List<GrantedAuthority> authorities = rolesString.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()); // 如果你在 token 中存储了权限信息

                if (username != null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            username, null, authorities);

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // 设置认证信息到安全上下文中
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

                User user = new User(username, authorities, claims);

                JwtAuthenticationToken authentication = new JwtAuthenticationToken(user, token, authorities);

                SecurityContextHolder.getContext().setAuthentication(authentication);

                // 新增：将用户信息设置到request属性中
                request.setAttribute("userId", username);
                request.setAttribute("userRoles", rolesString);

            }catch (JwtException e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                ResultVO errorResponse = ResultVO.authError();
                String jsonResponse = new ObjectMapper().writeValueAsString(errorResponse);

                response.getWriter().write(jsonResponse);
                return; // 直接返回，不再继续执行 filter chain
            }
            // 解析 token 中的信息
//            Claims claims = JwtUtils.parseJWT(token);

        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HEADER_STRING);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
