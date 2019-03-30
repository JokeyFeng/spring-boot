package com.jokey.bingo.service;

import com.jokey.bingo.entity.User;

import java.util.List;

/**
 * @author JokeyFeng
 * date:2019/3/21
 * project:spring-boot
 * package:com.jokey.bingo.service
 * comment:
 */
public interface UserService {

    /**
     * 保存
     *
     * @param user
     * @return
     */
    void save(User user);

    /**
     * 更新
     *
     * @param user
     */
    void update(User user);

    /**
     * 查询
     *
     * @param id
     * @return
     */
    User get(Integer id);

    /**
     * 删除
     *
     * @param id
     */
    void remove(Integer id);

    /**
     * 列表
     *
     * @return
     */
    List<User> findAll();

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return
     */
    User login(String username, String password);
}
