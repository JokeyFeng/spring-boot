package com.jokey.base.service.impl;

import com.jokey.base.db.MyMapper;
import com.jokey.base.service.BaseService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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

    /**
     * 实例化
     *
     * @return
     */
    protected abstract MyMapper<T> getMapper();

    @Override
    public List<T> selectAll() {
        return getMapper().selectAll();
    }

    @Override
    public T selectByKey(Object key) {
        return getMapper().selectByPrimaryKey(key);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(T entity) {
        return getMapper().insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(Object key) {
        return getMapper().deleteByPrimaryKey(key);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchDelete(List<String> list, String property, Class<T> clazz) {
        Example example = new Example(clazz);
        example.createCriteria().andIn(property, list);
        return getMapper().deleteByExample(example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateAll(T entity) {
        return getMapper().updateByPrimaryKey(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateNotNull(T entity) {
        return getMapper().updateByPrimaryKeySelective(entity);
    }

    @Override
    public List<T> selectByExample(Object example) {
        return getMapper().selectByExample(example);
    }
}
