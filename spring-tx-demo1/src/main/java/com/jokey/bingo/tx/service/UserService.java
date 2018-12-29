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

package com.jokey.bingo.tx.service;

import com.codingapi.tx.annotation.TxTransaction;
import com.jokey.base.entity.User;
import com.jokey.base.util.IdGenertor;
import com.jokey.bingo.tx.mapper.UserMapper;
import com.jokey.bingo.tx.third.SpringTxDemo2Feign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Project spring-boot
 * @Package com.jokey.bingo.service
 * @ClassName UserService
 * @Author JokeyZheng
 * @Date 2018/12/28
 * @Version 1.0
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SpringTxDemo2Feign txDemo2Feign;

    @Transactional(rollbackFor = Exception.class)
    public int insert(User user) {
        user.setId(IdGenertor.getUUID());
        return userMapper.insert(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public int update(User user) {
        return userMapper.updateByPrimaryKey(user);
    }

    @TxTransaction(isStart = true)
    @Transactional(rollbackFor = Exception.class)
    public int test0(User user) {
        int n = this.insert(user);
        int i = txDemo2Feign.insertUser(user);
        return n + i;
    }


    @TxTransaction(isStart = true)
    @Transactional(rollbackFor = Exception.class)
    public int test1(User user) {
        User uu = new User();
        uu.setName("郑境丰111");
        int i = txDemo2Feign.insertUser(uu);
        int n = this.insert(user);
        //模拟一个异常
        int ii = i / 0;
        return n + i;
    }
}
