package com.jokey.bigo.social.controller;

import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Zhengjingfeng
 * @created 2019/5/20 10:50
 * @comment
 */
public abstract class BaseController {
    /**
     * <p>
     * 获取项目在操作系统的绝对路径
     * </p>
     * 返回形如D:\工具\Tomcat-6.0\webapps\projectname\字符串 其中projectname为项目名称
     *
     * @return
     */
    protected String getRootAbsolutePath() {
        HttpServletRequest request = getRequest();
        return request.getSession().getServletContext().getRealPath("/");
    }

    /**
     * 得到应用的访问头地址 http://ip(域名):端口/应用名
     *
     * @return
     */
    protected String getBasePath() {
        HttpServletRequest request = getRequest();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        return basePath.concat(request.getContextPath());
    }

    /**
     * 获取web上下文
     *
     * @return
     */
    protected ServletContext getServletContext() {
        HttpServletRequest request = getRequest();
        return request != null ? request.getSession().getServletContext() : null;
    }

    /**
     * 获取HttpServletRequest
     *
     * @return
     */
    protected HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取HttpServletResponse
     *
     * @return
     */
    protected HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /**
     * 日期参数自动转换
     *
     * @param request
     * @param binder
     * @throws Exception
     */
    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        // 1. 使用spring自带的CustomDateEditor
        // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // binder.registerCustomEditor(Date.class, new
        // CustomDateEditor(dateFormat, true));
        // 2. 自定义的PropertyEditorSupport
       // binder.registerCustomEditor(Date.class, new DateConvertEditor());
        //binder.registerCustomEditor(String.class, new EmptyStringToNullConvertEditor());
    }
}
