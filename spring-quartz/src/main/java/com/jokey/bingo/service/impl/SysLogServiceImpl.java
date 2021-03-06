package com.jokey.bingo.service.impl;

import com.jokey.base.db.MyMapper;
import com.jokey.base.service.impl.BaseServiceImpl;
import com.jokey.bingo.entity.SysLog;
import com.jokey.bingo.mapper.SysLogMapper;
import com.jokey.bingo.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SysLogServiceImpl extends BaseServiceImpl<SysLog> implements SysLogService {
    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    protected MyMapper<SysLog> getMapper() {
        return sysLogMapper;
    }
}
