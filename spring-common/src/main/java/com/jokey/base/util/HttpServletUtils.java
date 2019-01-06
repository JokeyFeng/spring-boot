package com.jokey.base.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author JokeyFeng
 * date:2019/1/6
 * project:spring-boot
 * package:com.jokey.base.util
 * comment:
 */
public abstract class HttpServletUtils {

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }
}
