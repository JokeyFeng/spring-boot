package com.jokey.bingo.jpa;

import com.jokey.bingo.annotation.RedisCache;
import com.jokey.bingo.annotation.RedisEvict;
import com.jokey.bingo.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author JokeyFeng
 * date:2019/3/21
 * project:spring-boot
 * package:com.jokey.bingo.jpa
 * comment:
 */
@Repository
public interface UsersRepository extends CrudRepository<User, Integer> {

    /**
     * 根据账号密码查询
     *
     * @param name
     * @param pass
     * @return
     */
    @RedisCache(clazz = User.class)
    User findByNameAndPass(String name, String pass);

    /**
     * 主键删除
     *
     * @param id
     * @return
     */
    @RedisEvict(clazz = User.class)
    int deleteUserById(Integer id);
}
