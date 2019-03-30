package com.jokey.bingo.jpa;

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

    User findByNameAndPass(String name, String pass);
}
