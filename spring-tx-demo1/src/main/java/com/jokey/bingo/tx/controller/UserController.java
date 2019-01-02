/**
 * <html>
 * <body>
 * <P> Copyright©2015-2016 Yiheni. All rights reserved. </p>
 * <p> 粤ICP备16046232号-1 </p>
 * <p> Created on 2018/12/28</p>
 * <p> Created by JokeyZheng</p>
 * </body>
 * </html>
 */

package com.jokey.bingo.tx.controller;

import com.jokey.base.entity.User;
import com.jokey.bingo.tx.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Project spring-boot
 * @Package com.jokey.bingo.controller
 * @ClassName UserController
 * @Author JokeyZheng
 * @Date 2018/12/28
 * @Version 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/insert")
    public int insert(@RequestBody User user) {
        return userService.insert(user);
    }

    @PutMapping("/update")
    public int update(@RequestBody User user) {
        return userService.update(user);
    }

    @PostMapping("/test0")
    public int test0(@RequestBody User user) {
        return userService.test0(user);
    }

    @PostMapping("/test1")
    public int test1(@RequestBody User user) {
        return userService.test1(user);
    }

    @PostMapping("/test2")
    public int test2(@RequestPart User user) {
        return userService.test1(user);
    }
}
