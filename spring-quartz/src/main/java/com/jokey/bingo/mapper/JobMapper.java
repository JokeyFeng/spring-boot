package com.jokey.bingo.mapper;

import com.jokey.base.mapper.BaseMapper;
import com.jokey.bingo.entity.Job;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author JokeyFeng
 * date:2019/1/6
 * project:spring-boot
 * package:com.jokey.bingo.mapper
 * comment:
 */
@Component
public interface JobMapper extends BaseMapper<Job> {

    /**
     * 查询
     *
     * @return
     */
    List<Job> queryList();
}
