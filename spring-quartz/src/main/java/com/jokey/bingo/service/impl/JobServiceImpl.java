package com.jokey.bingo.service.impl;

import com.google.common.collect.Lists;
import com.jokey.base.annotation.CronTag;
import com.jokey.base.db.MyMapper;
import com.jokey.base.service.impl.BaseServiceImpl;
import com.jokey.bingo.entity.Job;
import com.jokey.bingo.mapper.JobMapper;
import com.jokey.bingo.service.JobService;
import com.jokey.bingo.util.QuartzUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author JokeyFeng
 * date:2019/1/6
 * project:spring-boot
 * package:com.jokey.bingo.service.impl
 * comment:
 */
@Slf4j
@Service("JobService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class JobServiceImpl extends BaseServiceImpl<Job> implements JobService {

    @Autowired
    private Scheduler scheduler;
    @Autowired
    private JobMapper jobMapper;

    @Override
    protected MyMapper<Job> getMapper() {
        return jobMapper;
    }

    /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    public void initJob() {
        List<Job> jobList = this.findAllJobs();
        jobList.forEach(job -> {
            CronTrigger cronTrigger = QuartzUtils.getCronTrigger(scheduler, job.getJobId());
            if (cronTrigger == null) {
                QuartzUtils.createScheduleJob(scheduler, job);
            } else {
                QuartzUtils.updateScheduleJob(scheduler, job);
            }
        });
    }

    @Override
    public Job findJob(Long jobId) {
        return this.selectByKey(jobId);
    }

    @Override
    public List<Job> findAllJobs() {
        return this.jobMapper.queryList();
    }

    @Override
    public List<Job> findAllJobs(Job job) {
        try {
            Example example = new Example(Job.class);
            Example.Criteria criteria = example.createCriteria();
            if (StringUtils.isNotBlank(job.getBeanName())) {
                criteria.andCondition("bean_name=", job.getBeanName());
            }
            if (StringUtils.isNotBlank(job.getMethodName())) {
                criteria.andCondition("method_name=", job.getMethodName());
            }
            if (StringUtils.isNotBlank(job.getStatus())) {
                criteria.andCondition("status=", Long.valueOf(job.getStatus()));
            }
            example.setOrderByClause("job_id");
            return this.selectByExample(example);
        } catch (Exception e) {
            log.error("获取任务失败", e);
            return Lists.newArrayList();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addJob(Job job) {
        job.setCreateTime(new Date());
        job.setStatus(Job.ScheduleStatus.PAUSE.getValue());
        int i = this.save(job);
        QuartzUtils.createScheduleJob(scheduler, job);
        return i;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateJob(Job job) {
        QuartzUtils.updateScheduleJob(scheduler, job);
        return this.updateNotNull(job);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBatch(String jobIds, String status) {
        List<String> idList = Arrays.asList(jobIds.split(","));
        Example example = new Example(Job.class);
        example.createCriteria().andIn("jobId", idList);
        Job job = new Job();
        job.setStatus(status);
        return this.jobMapper.updateByExampleSelective(job, example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBatch(String jobIds) {
        List<String> idList = Arrays.asList(jobIds.split(","));
        idList.forEach(id -> QuartzUtils.deleteScheduleJob(scheduler, Long.valueOf(id)));
        return this.batchDelete(idList, "jobId", Job.class);
    }

    @Override
    public void run(String jobIds) {
        List<String> idList = Arrays.asList(jobIds.split(","));
        idList.forEach(id -> QuartzUtils.run(scheduler, this.findJob(Long.valueOf(id))));
    }

    @Override
    public void pauseJob(String jobIds) {
        List<String> idList = Arrays.asList(jobIds.split(","));
        idList.forEach(id -> QuartzUtils.pauseJob(scheduler, Long.valueOf(id)));
    }

    @Override
    public void resumeJob(String jobIds) {
        List<String> idList = Arrays.asList(jobIds.split(","));
        idList.forEach(id -> QuartzUtils.resumeJob(scheduler, Long.valueOf(id)));
    }

    @Override
    public List<Job> getSysCronClazz() {
        Reflections reflections = new Reflections("com.jokey.bingo.task");
        List<Job> jobList = new ArrayList<>();
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(CronTag.class);
        for (Class clazz : annotated) {
            String clazzName = clazz.getSimpleName();
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                Job jj = new Job();
                String methodName = method.getName();
                Parameter[] parameters = method.getParameters();
                String params = String.format("%s(%s)", methodName, Arrays.stream(parameters)
                        .map(item -> item.getType().getSimpleName() + " " + item.getName()).collect(Collectors.joining(", ")));
                jj.setBeanName(StringUtils.uncapitalize(clazzName));
                jj.setMethodName(methodName);
                jj.setParams(params);

                jobList.add(jj);
            }
        }
        return jobList;
    }
}
