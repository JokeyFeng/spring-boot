package com.jokey.bigo.social.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zhengjingfeng
 * @created 2019/5/20 10:49
 * @comment
 */
@RestController
public class LoginController extends BaseController {

    /**
     * QQ登录
     *
     * @return
     */
    @GetMapping("/qq")
    public Object qqLogin() {
        return null;
    }

    /**
     * 微信登录
     *
     * @return
     */
    @GetMapping("/webChat")
    public Object webChatLogin() {
        return null;
    }

    /**
     * github登录
     *
     * @return
     */
    @GetMapping("/github")
    public Object githubLogin() {
        return null;
    }

    @GetMapping("/index")
    public Object index() {
        return "index";
    }
}
