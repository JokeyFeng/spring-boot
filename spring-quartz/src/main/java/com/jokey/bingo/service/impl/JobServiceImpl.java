package com.jokey.bingo.service.impl;

import com.jokey.base.service.impl.BaseServiceImpl;
import com.jokey.bingo.entity.Job;
import com.jokey.bingo.service.JobService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author JokeyFeng
 * date:2019/1/6
 * project:spring-boot
 * package:com.jokey.bingo.service.impl
 * comment:
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class JobServiceImpl extends BaseServiceImpl<Job> implements JobService {
}
