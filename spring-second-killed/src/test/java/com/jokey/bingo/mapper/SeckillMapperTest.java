package com.jokey.bingo.mapper;


import com.jokey.bingo.SecondKillApplication;
import com.jokey.bingo.entity.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SecondKillApplication.class)
public class SeckillMapperTest {

    @Resource
    SeckillMapper seckillMapper;



    @Test
    public void reduceNumber() {
        long id = 1000;
        Date date = new Date();
        int count = seckillMapper.reduceNumber(id, date);
        System.out.println(count);
    }

    @Test
    public void queryById() {
        long id = 4;
        Seckill entity = seckillMapper.queryById(id);
        System.out.println(entity.toString());
    }

    @Test
    public void queryAll() {
        List<Seckill> list = seckillMapper.queryAll(0, 10);
        System.out.println(list);
    }
}
