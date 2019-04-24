package com.jokey.bingo.controller;

import com.jokey.bingo.entity.LoginParam;
import com.jokey.bingo.entity.ResponseData;
import com.jokey.bingo.entity.User;
import com.jokey.bingo.service.UserService;
import com.jokey.bingo.util.JWTUtils;
import com.jokey.bingo.util.UserInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
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

  //  @EnableAuth
    @PostMapping("/save")
    public ResponseData<String> save(@RequestBody User user) {
        user.setStatus(true);
        userService.save(user);
        return new ResponseData<>("保存成功");
    }

  //  @EnableAuth
    @GetMapping("/get")
    public ResponseData<User> get(@RequestParam Integer id) {
        return new ResponseData<>(userService.get(id));
    }

  //  @EnableAuth
    @PutMapping("/update")
    public ResponseData<String> update(@RequestBody User user) {
        userService.update(user);
        return new ResponseData<>("修改成功");
    }

  //  @EnableAuth
    @DeleteMapping("/remove")
    public ResponseData<String> remove(@RequestParam Integer id) {
        userService.remove(id);
        return new ResponseData<>("删除成功");
    }

    // @EnableAuth
    @GetMapping("/list")
    public ResponseData<List<User>> list(HttpServletRequest request) {
        System.out.println("登录用户的ID->" + UserInfoUtil.getLoginUserId(request));
        return new ResponseData<>(userService.findAll());
    }

    @PostMapping("/login")
    public Object login(@RequestBody LoginParam loginParam) {
        Map<String, Object> result = new HashMap<>();
        User user = userService.login(loginParam.getName(), loginParam.getPass());
        if (user != null) {
            result.put("status", true);
            result.put("token", JWTUtils.getInstance().getToken(user.getId() + "", 10));
        } else {
            result.put("status", false);
        }
        return result;
    }
}
