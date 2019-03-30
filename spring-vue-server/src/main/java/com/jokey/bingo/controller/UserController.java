package com.jokey.bingo.controller;

import com.jokey.bingo.util.JWTUtils;
import com.jokey.bingo.entity.LoginParam;
import com.jokey.bingo.entity.User;
import com.jokey.bingo.service.UserService;
import com.jokey.bingo.util.UserInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author JokeyFeng
 * date:2019/3/21
 * project:spring-boot
 * package:com.jokey.bingo.controller
 * comment:
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public Object save(@RequestBody User user) {
        user.setStatus(true);
        userService.save(user);
        return true;
    }

    @GetMapping("/get")
    public Object get() {
        return userService.get(1);
    }

    @GetMapping("/update")
    public Object update() {
        User user = userService.get(1);
        user.setAge(60);
        userService.update(user);
        return true;
    }

    @DeleteMapping("/remove")
    public Object remove(@RequestParam Integer id) {
        userService.remove(id);
        return true;
    }

    @GetMapping("/list")
    public Object list(HttpServletRequest request) {
        System.out.println("登录用户的ID->" + UserInfoUtil.getLoginUserId(request));
        return userService.findAll();
    }

    @PostMapping("/login")
    public Object login(@RequestBody LoginParam loginParam) {
        Map<String, Object> result = new HashMap<>();
        User user = userService.login(loginParam.getUsername(), loginParam.getPassword());
        if (user != null) {
            result.put("status", true);
            result.put("token", JWTUtils.getInstance().getToken(user.getId() + ""));
        } else {
            result.put("status", false);
        }
        return result;
    }
}
