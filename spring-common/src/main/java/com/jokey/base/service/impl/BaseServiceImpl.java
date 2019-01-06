package com.jokey.base.service.impl;

import com.jokey.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author JokeyFeng
 * date:2019/1/6
 * project:spring-boot
 * package:com.jokey.base.service.impl
 * comment:
 */
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    @Autowired
    protected Mapper<T> mapper;

    public Mapper<T> getMapper() {
        return this.mapper;
    }

    @Override
    public List<T> selectAll() {
        return this.mapper.selectAll();
    }

    @Override
    public T selectByKey(Object key) {
        return this.mapper.selectByPrimaryKey(key);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(T entity) {
        return this.mapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(Object key) {
        return this.mapper.deleteByPrimaryKey(key);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchDelete(List<String> list, String property, Class<T> clazz) {
        Example example = new Example(clazz);
        example.createCriteria().andIn(property, list);
        return this.mapper.deleteByExample(example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateAll(T entity) {
        return this.mapper.updateByPrimaryKey(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateNotNull(T entity) {
        return this.mapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public List<T> selectByExample(Object example) {
        return this.mapper.selectByExample(example);
    }
}
