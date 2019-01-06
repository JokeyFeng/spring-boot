package com.jokey.base.service;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author JokeyFeng
 * date:2019/1/6
 * project:spring-boot
 * package:com.jokey.base.service
 * comment:
 */
@Service
public interface BaseService<T> {
    /**
     * 获取所有
     *
     * @return
     */
    List<T> selectAll();

    /**
     * 主键查询
     *
     * @param key
     * @return
     */
    T selectByKey(Object key);

    /**
     * 新增
     *
     * @param entity
     * @return
     */
    int save(T entity);

    /**
     * 主键删除
     *
     * @param key
     * @return
     */
    int delete(Object key);

    /**
     * 批量删除
     *
     * @param list
     * @param property
     * @param clazz
     * @return
     */
    int batchDelete(List<String> list, String property, Class<T> clazz);

    /**
     * 更新全部字段
     *
     * @param entity
     * @return
     */
    int updateAll(T entity);

    /**
     * 更新
     *
     * @param entity
     * @return
     */
    int updateNotNull(T entity);

    /**
     * 根据条件查询
     *
     * @param example
     * @return
     */
    List<T> selectByExample(Object example);

}
