package com.jokey.bingo.util;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author JokeyFeng
 * date:2019/3/26
 * project:spring-boot
 * package:com.jokey.bingo.util
 * comment:
 */
public class UserInfoUtil {

    public static String getLoginUserId(HttpServletRequest request) {

        String authorization = request.getHeader("authorization");
        if (StringUtils.hasText(authorization)) {
            JWTUtils.JWTResult jwtResult = JWTUtils.getInstance().checkToken(authorization);
            if (jwtResult.isStatus()) {
                return jwtResult.getUid();
            }
        }
        return null;
    }
}
