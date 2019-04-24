package com.jokey.bingo.service;

import com.jokey.bingo.entity.User;
import com.jokey.bingo.jpa.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author JokeyFeng
 * date:2019/3/21
 * project:spring-boot
 * package:com.jokey.bingo.service
 * comment:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(User user) {
        usersRepository.save(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(User user) {
        User old = this.get(user.getId());
        old.setAge(user.getAge());
        old.setName(user.getName());
        usersRepository.save(old);
    }

    @Override
    public User get(Integer id) {
        return usersRepository.findById(id).orElse(new User());
    }

    @Override
    public User login(String username, String password) {
        return usersRepository.findByNameAndPass(username, password);
    }

    @Override
    public List<User> findAll() {
        Iterator<User> iterator = usersRepository.findAll().iterator();

        List<User> list = new ArrayList<>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(Integer id) {
        //usersRepository.deleteById(id);
        usersRepository.deleteUserById(id);
    }
}
