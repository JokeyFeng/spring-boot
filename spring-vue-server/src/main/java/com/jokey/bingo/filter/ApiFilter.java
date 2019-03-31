package com.jokey.bingo.filter;

import com.alibaba.fastjson.JSON;
import com.jokey.bingo.auth.ApiAuthDataInit;
import com.jokey.bingo.entity.ResponseCode;
import com.jokey.bingo.util.JWTUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * @author JokeyFeng
 * date:2019/3/27
 * project:spring-boot
 * package:com.jokey.bingo
 * comment:
 */

@Order(10)
@Component
@WebFilter(value = "apiFilter", urlPatterns = {"/*"})
public class ApiFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        //预检请求不允许设置指定的content-type
        if (!HttpMethod.OPTIONS.matches(request.getMethod())) {
            //跨域响应需要设置Access-Control-Allow-Origin
            response.setContentType("application/json;charset=utf-8");
            response.setHeader("Access-Control-Allow-Origin", "*");
            if (ApiAuthDataInit.checkApis.contains(request.getRequestURI())) {
                String authorization = request.getHeader("authorization");
                if (StringUtils.hasText(authorization)) {
                    JWTUtils.JWTResult jwtResult = JWTUtils.getInstance().checkToken(authorization);
                    //过期
                    if (!jwtResult.isStatus()) {
                        PrintWriter writer = response.getWriter();
                        writer.write(JSON.toJSONString(jwtResult));
                        writer.flush();
                        return;
                    }

                } else {
                    PrintWriter writer = response.getWriter();
                    writer.write(JSON.toJSONString(new JWTUtils.JWTResult(false, null, "非法请求", ResponseCode.NO_AUTH_CODE.getCode())));
                    writer.flush();
                    return;
                }
            }

        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
