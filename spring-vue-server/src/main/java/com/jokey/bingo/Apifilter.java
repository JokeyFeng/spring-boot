package com.jokey.bingo;

import com.jokey.bingo.util.JWTUtils;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author JokeyFeng
 * date:2019/3/27
 * project:spring-boot
 * package:com.jokey.bingo
 * comment:
 */
public class Apifilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String authorization = request.getHeader("Authorization");
        if (StringUtils.hasText(authorization)) {
            JWTUtils.JWTResult jwtResult = JWTUtils.getInstance().checkToken(authorization);
            //过期
            if (!jwtResult.isStatus() && jwtResult.getCode() == 402) {
                PrintWriter writer = response.getWriter();
                writer.write("token timeout");
                writer.flush();
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
