package com.jokey.bingo.service.impl;

import com.google.common.collect.Lists;
import com.jokey.base.db.MyMapper;
import com.jokey.base.service.impl.BaseServiceImpl;
import com.jokey.bingo.entity.JobLog;
import com.jokey.bingo.mapper.JobLogMapper;
import com.jokey.bingo.service.JobLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;

/**
 * @author JokeyFeng
 * date:2019/1/6
 * project:spring-boot
 * package:com.jokey.bingo.service.impl
 * comment:
 */
@Slf4j
@Service("JobLogService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class JobLogServiceImpl extends BaseServiceImpl<JobLog> implements JobLogService {

    @Autowired
    private JobLogMapper jobLogMapper;

    @Override
    protected MyMapper<JobLog> getMapper() {
        return jobLogMapper;
    }

    @Override
    public List<JobLog> findAllJobLogs(JobLog jobLog) {
        try {
            Example example = new Example(JobLog.class);
            Example.Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(jobLog.getBeanName())) {
                criteria.andCondition("bean_name=", jobLog.getBeanName());
            }

            if (StringUtils.isNotBlank(jobLog.getMethodName())) {
                criteria.andCondition("method_name=", jobLog.getMethodName());
            }

            if (StringUtils.isNotBlank(jobLog.getStatus())) {
                criteria.andCondition("status=", Long.valueOf(jobLog.getStatus()));
            }
            example.setOrderByClause("log_id desc");
            return this.selectByExample(example);
        } catch (NumberFormatException e) {
            log.error("获取调度日志失败-->{}", e);
            return Lists.newArrayList();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveJobLog(JobLog jobLog) {
        return this.save(jobLog);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBatch(String jobLogIds) {
        List<String> list = Arrays.asList(jobLogIds.split(","));
        return this.batchDelete(list, "logId", JobLog.class);
    }
}
